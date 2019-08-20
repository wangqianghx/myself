package com.self.wq.freemarker.service.impl;

import com.self.wq.freemarker.FreeMarkerTools;
import com.self.wq.freemarker.dao.FlowManagerDao;
import com.self.wq.freemarker.dao.FlowNodeDao;
import com.self.wq.freemarker.dao.TableColDao;
import com.self.wq.freemarker.domain.TableCol;
import com.self.wq.freemarker.domain.FlowNode;
import com.self.wq.freemarker.service.FreeMarkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: wq
 * @date: 2018/9/4 9:12
 */
@Service
public class FreeMarkerServiceImpl implements FreeMarkerService {

    @Autowired
    FlowManagerDao flowManagerDao;

    @Autowired
    FlowNodeDao flowNodeDao;

    @Autowired
    TableColDao tableColDao;

    private boolean devEnvFlag = true;

    @Override
    public StringWriter buildOneFlow() {

        StringWriter writer = new StringWriter();

        List<TableCol> l = tableColDao.findAllTableCol();
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < l.size(); i++) {
            map.put(l.get(i).getTableName() + l.get(i).getColName(), l.get(i).getIndexNum());
        }
        List<FlowNode> rows = flowNodeDao.findFlowNodeByFlowCode("jsyjbc");

        writer.write(buildTopCode());

        for (int i = 0; i < rows.size(); i++) {
            writer.write(buildFile2RDD(rows.get(i), map));
        }

        writer.write(buildDownCode());

        return writer;
    }

    public static String buildTopCode(){
        String result = "package com.yss.bigdata.ta.achievementpay\n" +
                "\n" +
                "import com.yss.bigdata.ta.analyticutil.{AnalyticFormula, ArithHelper, FormulaComputing}\n" +
                "import java.util.{Stack, HashMap => jhashmap, Map => jmap}\n" +
                "import org.apache.spark.sql.SparkSession\n" +
                "import scala.collection.mutable.ListBuffer\n" +
                "\n" +
                "object TopRDD {\n" +
                "  def main(args: Array[String]): Unit = {\n" +
                "    val sparkSql = SparkSession.builder().master(\"local\").appName(\"TestAchievementpay\").getOrCreate()\n" +
                "    val sc = sparkSql.sparkContext\n";

        return result;
    }

    public String buildFile2RDD(FlowNode r, Map<String,String> map){
        String result = "";
        if (r.getTablename() != "") {
            result += "   val " + r.getTablename() + "_path = \"" + r.getPrefixStr() + r.getTablename() + r.getSuffixStr() + "\"\n" +
                    "    val " + r.getTablename() + "_rdd = sc.textFile(" + r.getTablename() + "_path, 1)\n";
            result += buildPrintln(r.getTablename() + "_rdd");
        }
        if ("groupbykey".equals(r.getFlowNodeType())) {
            result += "      val tempRDD" + r.getFlowNodeCode() + "  = " + buildGroupBy(r, map, false, true);
        }
        if ("leftOuterJoin".equals(r.getFlowNodeType())) {
            result += "      " + buildLeftOuterJoin(r, map);
        }
        if("join".equals(r.getFlowNodeType())){
            result += "      " + buildJoin(r, map);
        }
        return result;
    }

    public String buildJoin(FlowNode r, Map<String,String> map){
        String result = "";
        String tempRDD = "tmpRDD" + date2strAndRandom();

        result += "val tmpRDDJoin" + r.getFlowNodeCode() + " = " + buildGroupBy(r, map, true, false);

        result += buildPrintln("tmpRDDJoin" + r.getFlowNodeCode());

        result += "val " + tempRDD + " = tempRDD" + r.getFromFlowNodeCode() + "." + r.getFlowNodeType() + "(tmpRDDJoin" + r.getFlowNodeCode() + ")\n";

        result += buildPrintln(tempRDD);

        result += "val tempRDD" + r.getFlowNodeCode() + " = " + tempRDD+ ".flatMap(line => {\n" +
                "      val value_all = new ListBuffer[(String)]()\n" +
                "      val temp2 = line._2._1.toList(0)\n" +
                "      val achievementpayrate = line._2._2\n" +
                "      value_all.append(achievementpayrate + \"\\t\" + temp2)\n" +
                "      value_all.toList\n" +
                "    })";

        if(r.getToRowkey() == null || r.getToRowkey() == ""){
            result += "\n";
        }else{
            result += ".groupByKey()\n";
        }

        result += buildPrintln("tempRDD" + r.getFlowNodeCode());

        return result;
    }

    public String buildLeftOuterJoin(FlowNode r, Map<String,String> map){
        String result = "";
        String tempRDD = "tmpRDD" + date2strAndRandom();

        result += "val tmpRDDLeftOuterJoin" + r.getFlowNodeCode() + " = " + buildGroupBy(r, map, true, true);

        result += buildPrintln("tmpRDDLeftOuterJoin" + r.getFlowNodeCode());

        result += "val " + tempRDD + " = tempRDD" + r.getFromFlowNodeCode() + "." + r.getFlowNodeType() + "(tmpRDDLeftOuterJoin" + r.getFlowNodeCode() + ")\n";

        result += buildPrintln(tempRDD);

        result += "val tempRDD" + r.getFlowNodeCode() + " = " + tempRDD+ ".flatMap(line => {\n" +
                "      val value_all = new ListBuffer[(String, String)]()\n" +
                "      for (i <- line._2._1){\n" +
                "        for(j <- line._2._2.get.toList){\n" +
                "          value_all.append((j.split(\"\\t\")(15), i + \"\\t\" + j))\n" +
                "        }\n" +
                "      }\n" +
                "      value_all.toList\n" +
                "    })";

        if(r.getToRowkey() == null || r.getToRowkey() == ""){
            result += "\n";
        }else{
            result += ".groupByKey()\n";
        }

        result += buildPrintln("tempRDD" + r.getFlowNodeCode());

        return result;
    }

    public String buildGroupBy(FlowNode r, Map<String,String> map, boolean joinFlag, boolean isGroupByKey){
        String[] arr = null;
        if(joinFlag){
            arr = r.getJoinRowKey().split(",");
        }else{
            arr = r.getToRowkey().split(",");
        }
        String keys = "";
        for (int i = 0; i<arr.length;i++) {
            keys += "keys(" + map.get(arr[i]) + ")";
            if (i != arr.length - 1){
                keys += " + \"\\t\" + ";
            }
        }
        String result = r.getTablename() + "_rdd.map(line => {\n" +
                "       val keys = line.split(\"\\t\")\n" +
                "       (" + keys + ", line)\n" +
                "       })";

        if(isGroupByKey){
            result += ".groupByKey()\n";
        }else{
            result += "\n";
        }

        result += buildPrintln(r.getTablename() + "_rdd");

        return result;
    }

    public String iteratorTableCol(Map<String,String> map){

        String result = "";

        for (int i = 0; i < map.size(); i++){
//            result += "         val " + tc.get(i).getTableName()+ "XXXX" + tc.get(i).getColName() + " = keys(" + i + ")\n";
        }

        return result;
    }

    public String buildDownCode(){

        return "}}";
    }

    public String buildPrintln(String rddName){
        if(devEnvFlag){
            return rddName + ".foreach(line => println(\"" + rddName + "::::\" + line))\n";
        }
        return "";
    }

    public String date2strAndRandom(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
