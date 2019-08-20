package com.self.wq.freemarker;


import java.io.StringWriter;

/**
 * @author: wq
 * @date: 2018/9/3 10:07
 */
public class Main {
    public static void main(String[] args) {
//        Main m = new Main();
//        m.buildFTL();
        StringWriter writer = new StringWriter();
        writer.write("1231");
        writer.write("aaaaa");
        System.out.println(writer.toString());
    }

//    public void buildFTL(){
//        Configuration configuration = new Configuration();
//        try {
//            configuration.setDirectoryForTemplateLoading(new File("C:\\Users\\LoveXueBaby\\IdeaProjects\\myself\\wqutil\\src\\main\\resources\\ftl"));
//            System.out.println(getClass().getResource("/").getPath() + File.separator + "freemarker");
//            Template template = configuration.getTemplate("topRDD.ftl");
//            StringWriter writer = new StringWriter();
//            Map<String, Object> context = new HashMap<String, Object>();
//            context.put("env", "dev");
//
//            TableCol tc = new TableCol();
//            tc.setName("test1");
//            List<TableCol> l = new ArrayList<TableCol>();
//            l.add(tc);
//
//            FlowNode tPOJO = new FlowNode();
//            tPOJO.setTablename("T_TA_APPTRADEBLOTTER");
//            tPOJO.setFlowNodeCode("12");
//            tPOJO.setFlowNodeName("12");
//            tPOJO.setFlowCode("12");
//            tPOJO.setFlowType("groupbykey");
//            tPOJO.setFlowName("12");
//            tPOJO.setToRowkey("ffundid + \"\\t\" + apkind");
//            tPOJO.setTableCols(l);
//            tPOJO.setPrefixStr("C:\\\\wq_tools\\\\gitlab\\\\yss\\\\bigdata_ta\\\\com.yss.bigdata.ta\\\\src\\\\main\\\\resources\\\\hdfsfile\\\\");
//            tPOJO.setSuffixStr(".txt");
//
//            FlowNode tPOJO1 = new FlowNode();
//            tPOJO1.setTablename("T_TA_ACHIEVEMENTPAYCALTYPE");
//            tPOJO1.setFlowNodeCode("123");
//            tPOJO1.setFlowNodeName("123");
//            tPOJO1.setFlowCode("123");
//            tPOJO1.setFlowType("leftOuterJoin");
//            tPOJO1.setFlowName("123");
//            tPOJO1.setToRowkey("fpd_code + \"\\t\" + fachievementpaytype");
//            tPOJO1.setTableCols(l);
//            tPOJO1.setPrefixStr("C:\\\\wq_tools\\\\gitlab\\\\yss\\\\bigdata_ta\\\\com.yss.bigdata.ta\\\\src\\\\main\\\\resources\\\\hdfsfile\\\\");
//            tPOJO1.setSuffixStr(".txt");
//            List<FlowNode> rows = new ArrayList<FlowNode>();
//            rows.add(tPOJO);
//            rows.add(tPOJO1);
//            context.put("flowRows", rows);
//
//            template.process(context, writer);
//            System.out.println(writer.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (TemplateException e) {
//            e.printStackTrace();
//        }
//    }

}
