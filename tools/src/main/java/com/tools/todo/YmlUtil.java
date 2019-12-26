//package com.tools.todo;
//
//import com.yss.common.constant.CommonConstant;
//import com.yss.common.vo.YamlFlowVo;
//import org.ho.yaml.Yaml;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 描述：读写yml文件工具类
// *
// * @author gongzhijing at 2018/12/11 17:05
// * @version 1.0.0
// */
//public class YmlUtil {
//
//    /**
//     * 描述：加载yml文件，读取内容
//     *
//     * @param path 文件路径
//     * @return 结果值
//     * @author gongzhijing at 2018/12/11 17:06:43
//     */
//    public static Object load(String path) {
//
//        Object result = null;
//        try {
//            File file = new File(path);
//            if (!file.exists()) {
//                throw new RuntimeException("文件不存在，请检查路径 ！！");
//            }
//            FileInputStream fs = new FileInputStream(file);
//            //读入文件
//            result = Yaml.load(fs);
//            fs.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    /**
//     * 描述：加载多个yml文件，读取所有文件内容
//     *
//     * @param path 路径
//     * @return 对象集合
//     * @author zhangyu at 2018/12/19 10:56
//     */
//    public static List<Object> loadAll(String path) {
//
//        //初始化读取的yml文件封装结果集
//        List<Object> ymlResult = new ArrayList<>();
//        //递归得到所有YML文件集合
//        List<String> fileList = FileUtils.scanDir(path,CommonConstant.YML_FIX);
//        //读取所有文件内容
//        if(fileList!=null && fileList.size()>0){
//            for (String filepath : fileList) {
//                ymlResult.add(load(filepath));
//            }
//        }
//        return ymlResult;
//    }
//
//
//    /**
//     * 描述：写入yml文件,一组配置,不以三个横杠分开
//     * 内容支持javabean,map,set,常量等...
//     *
//     * @param path   文件路径
//     * @param object 文件内容
//     * @author gongzhijing at 2018/12/11 17:15:57
//     */
//    public static void write(String path, Object object) {
//
//        try {
//            //创建文件夹
//            File dumpfile = new File(path);
//            File parentFile = dumpfile.getParentFile();
//            if (!parentFile.exists()) {
//                parentFile.mkdirs();
//            }
//            //创建文件
//            dumpfile.createNewFile();
//            //文件未创建成功
//            if (!dumpfile.exists()) {
//                throw new RuntimeException("文件不存在，请检查路径 ！！");
//            }
//            Yaml.dump(object, dumpfile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    /**
//     * 描述：测试yml文件工具类
//     *
//     * @author gongzhijing at 2018/11/2 10:36:00
//     */
//    public static void main(String[] args) {
//
//        String path = "C:\\导出文件\\YML\\YmlFlowVo.yml";
//
//        //写入文件
//       YamlFlowVo flow = new YamlFlowVo();
//        YamlFlowVo flow1 = new YamlFlowVo();
//        flow1.setFlowName("检查是否触发最低持有");
//        flow1.setBeanId("checkHoldIngServiceImpl");
//        YamlFlowVo flow2 = new YamlFlowVo();
//        flow2.setFlowName("计算确认份额");
//        flow2.setBeanId("ackForceServiceImpl");
//        YamlFlowVo flow3 = new YamlFlowVo();
//        flow3.setFlowName("计算余额构成");
//        flow3.setBeanId("countBalconsServiceImpl");
//        YamlFlowVo flow4 = new YamlFlowVo();
//        flow4.setFlowName("计算费用");
//        flow4.setBeanId("countExpensesServiceImpl");
//        YamlFlowVo flow5 = new YamlFlowVo();
//        flow5.setFlowName("计算违约赎回费");
//        flow5.setBeanId("pddefaultredenServiceImpl");
//        List<YamlFlowVo> list = new ArrayList<>();
//        list.add(flow1);
//        list.add(flow2);
//        list.add(flow3);
//        list.add(flow4);
//        list.add(flow5);
//        flow.setChildrenFlow(list);
//        YmlUtil.write(path, flow);
//
//        //读取文件
//        YamlFlowVo object = (YamlFlowVo) YmlUtil.load(path);
//        List<YamlFlowVo> listf = object.getChildrenFlow();
//        for (YamlFlowVo bean : listf) {
//            System.out.println(bean.getBeanId());
//        }
//
//
//
//
//    }
//
//}
