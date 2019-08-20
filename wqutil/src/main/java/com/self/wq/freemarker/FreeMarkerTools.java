package com.self.wq.freemarker;

import com.self.wq.freemarker.domain.FlowNode;
import com.self.wq.freemarker.domain.TableCol;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: wq
 * @date: 2018/9/4 9:18
 */
public class FreeMarkerTools {

//    public static String buildTopCode(){
//        String result = "package com.yss.bigdata.ta.achievementpay\n" +
//                "\n" +
//                "import com.yss.bigdata.ta.analyticutil.{AnalyticFormula, ArithHelper, FormulaComputing}\n" +
//                "import java.util.{Stack, HashMap => jhashmap, Map => jmap}\n" +
//                "import org.apache.spark.sql.SparkSession\n" +
//                "import scala.collection.mutable.ListBuffer\n" +
//                "\n" +
//                "object TopRDD {\n" +
//                "  def main(args: Array[String]): Unit = {\n" +
//                "    val sparkSql = SparkSession.builder().master(\"local\").appName(\"TestAchievementpay\").getOrCreate()\n" +
//                "    val sc = sparkSql.sparkContext\n";
//
//        return result;
//    }
//
////    public static String buildFile2RDD(FlowNode r, Map<String,String> tc){
////        String result = "   val " + r.getTablename() + "_path = \"" + r.getPrefixStr() + r.getTablename() + r.getSuffixStr() + "\"\n" +
////                "    val " + r.getTablename() + "_rdd = sc.textFile(" + r.getTablename() + "_path, 1)\n";
////        if("groupbykey".equals(r.getFlowNodeType())){
////            result += "      topRDD = " + buildGroupBy(r, tc);
////        }
////        if("leftOuterJoin".equals(r.getFlowNodeType())){
////            result += "      " + buildLeftOuterJoin(r, tc);
////        }
////        return result;
////    }
//
//    public static String buildLeftOuterJoin(FlowNode r, Map<String,String> tc){
//        String result = "";
//        String tempRDD = "tmpRDD" + date2strAndRandom();
//
//        result += "val " + tempRDD + " = " + buildGroupBy(r, tc);
//        result += "topRDD = topRDD." + r.getFlowNodeType() + "(" + tempRDD + ")\n";
//        result += "topRDD = topRDD.flatMap(line => {\n" +
//                "      val value_all = new ListBuffer[(String, String)]()\n" +
//                "      for (i <- line._2._1){\n" +
//                "        for(j <- line._2._2.get.toList){\n" +
//                "          value_all.append((j.split(\"\\t\")(15), i + \"\\t\" + j))\n" +
//                "        }\n" +
//                "      }\n" +
//                "      value_all.toList\n" +
//                "    }).groupByKey()\n";
//
//        return result;
//    }
//
//    public static String buildGroupBy(FlowNode r, Map<String,String> map){
//        String[] arr = r.getToRowkey().split(",");
//        String keys = "";
//        for (int i = 0; i<arr.length;i++) {
//            keys += "keys(" + map.get(arr[i]) + ")";
//            if (i != arr.length - 1){
//                keys += " + \"\\t\" + ";
//            }
//        }
//        String result = r.getTablename() + "_rdd.map(line => {\n" +
//        "       val keys = line.split(\"\\t\")\n" +
//                "       (" + keys + ", line)\n" +
//                "       }).groupByKey()\n";
//
//        return result;
//    }
//
//    public static String iteratorTableCol(Map<String,String> tc){
//
//        String result = "";
//
//        for (int i = 0; i < tc.size(); i++){
////            result += "         val " + tc.get(i).getTableName()+ "XXXX" + tc.get(i).getColName() + " = keys(" + i + ")\n";
//        }
//
//        return result;
//    }
//
//    public static String buildDownCode(){
//
//        return "}}";
//    }
//
//    public static String date2strAndRandom(){
//        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//    }
}
