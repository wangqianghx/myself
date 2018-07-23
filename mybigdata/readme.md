# Hadoop组件安装文档

1、 版本建议

名称|版本号
---|---
Centos | V7.5
Java | V1.8
Hadoop | V2.7.6
Hive | V2.3.3
Mysql | V5.7
Spark | V2.3
Scala | V2.12.6
Flume | V1.80
Sqoop | V1.4.5

2、 Hadoop基础组件下载

[JDK地址](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

[Hadoop地址页](http://hadoop.apache.org/releases.html)

[Hadoop地址](http://www.apache.org/dyn/closer.cgi/hadoop/common/hadoop-2.7.6/hadoop-2.7.6.tar.gz)

[Hive地址1](http://www.apache.org/dyn/closer.cgi/hive/)

[Hive地址2](http://ftp.jaist.ac.jp/pub/apache/hive/)

[Spark地址页](http://spark.apache.org/downloads.html)

[Spark地址](https://www.apache.org/dyn/closer.lua/spark/spark-2.3.1/spark-2.3.1-bin-hadoop2.7.tgz)

[Scala地址页](https://www.scala-lang.org/download/2.12.6.html)

[Scala地址](https://downloads.lightbend.com/scala/2.12.6/scala-2.12.6.tgz)

[Flume地址1](http://www.apache.org/dyn/closer.lua/flume/1.8.0/apache-flume-1.8.0-bin.tar.gz)

[Flume地址2](http://ftp.meisei-u.ac.jp/mirror/apache/dist/flume/1.8.0/apache-flume-1.8.0-bin.tar.gz)


3、 Centos7基本组件安装

```
$ yum install net-tools.x86_64 vim* wget.x86_64 ntp -y
```

4、 ssh免密码
```
$ ssh-keygen -t rsa -P '' -f ~/.ssh/id_rsa
$ cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
$ chmod 0600 ~/.ssh/authorized_keys
```

> 验证配置
```
$ ssh localhost
```

5、 Hadoop组件安装

* 创建软件下载目录
```
$ mkdir -p /opt
$ cd /opt
```
* 解压
```
$ tar -zxvf 包名
```

6、 配置环境变量

* 修改/etc/profile

```
export JAVA_HOME=/opt/jdk1.8.0_181
export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
export HIVE_HOME=/opt/apache-hive-2.3.3-bin
export HADOOP_HOME=/opt/hadoop-2.7.6
export HADOOP_CONF_DIR=/opt/hadoop-2.7.6/etc/hadoop
export YARN_CONF_DIR=/opt/hadoop-2.7.6/etc/hadoop
export SCALA_HOME=/opt/scala-2.12.6
export SPARK_HOME=/opt/spark-2.3.1-bin-hadoop2.7

export PATH=$JAVA_HOME/bin:$PATH
export PATH=$PATH:$HADOOP_HOME/bin:$HADOOP_HOME/sbin:
export PATH=$PATH:$HIVE_HOME/bin:
export PATH=$SCALA_HOME/bin:$SPARK_HOME/bin:$PATH
```

* 环境变量立即生效

```
$ source /etc/profile
```

7、 hadoop配置

* 增加配置hadoop-env.sh
```
export JAVA_HOME=/opt/jdk1.8
```
* 修改配置文件，在目录$HADOOP_HOME/etc/hadoop/下

1. core-site.xml:
    ```
    <configuration>
        <property>
            <name>fs.defaultFS</name>
            <value>hdfs://hadoop001:9000</value>
        </property>
        <property>
            <name>hadoop.tmp.dir</name>
            <value>/root/hdfs/hdfs_tmp</value>
        </property>
        <property>
            <name>io.file.buffer.size</name>
            <value>4096</value>
        </property>
    </configuration>
    ```
2. hdfs-site.xml:
    ```
    <configuration>
      <property>
            <name>dfs.namenode.name.dir</name>
            <value>/root/hdfs/name</value>
        </property>
        <property>
            <name>dfs.datanode.data.dir</name>
            <value>/root/hdfs/data</value>
        </property>
        <property>
            <name>dfs.replication</name>
            <value>1</value>
        </property>
        <property>
            <name>dfs.namenode.secondary.http-address</name>
            <value>hadoop001:9001</value>
        </property>
        <property>
            <name>dfs.webhdfs.enabled</name>
            <value>true</value>
        </property>
    </configuration>
    ```
3. mapred-site.xml:
    ```<configuration>  
    <property>
        <name>mapreduce.framework.name</name>
        <value>yarn</value>
      </property>
      <property>
        <name>mapreduce.jobhistory.address </name>
        <value>hadoop001:10020</value>
      </property>
      <property>
        <name>mapreduce.jobhistory.webapp.address</name>
        <value>hadoop001:19888</value>
      </property>
    </configuration>
    ```

* yarn-site.xml(yarn的配置安装系统为8G内存配置):
    ```
    <configuration>
    <property>
    <name>yarn.nodemanager.aux-services</name>
    <value>mapreduce_shuffle</value>
    </property>
    <property>
    <name>yarn.nodemanager.auxservices.mapreduce.shuffle.class</name>
    <value>org.apache.hadoop.mapred.ShuffleHandler</value>
    </property>
    <property>
    <name>yarn.resourcemanager.address</name>
    <value>hadoop001:8032</value>
    </property>
    <property>
    <name>yarn.resourcemanager.scheduler.address</name>
    <value>hadoop001:8030</value>
    </property>
    <property>
    <name>yarn.resourcemanager.resource-tracker.address</name>
    <value>hadoop001:8031</value>
    </property>
    <property>
    <name>yarn.resourcemanager.admin.address</name>
    <value>hadoop001:8033</value>
    </property>
    <property>
    <name>yarn.log-aggregation-enable</name>
    <value>true</value>
    </property>
    <property>
    <name>yarn.log-aggregation.retain-seconds</name>
    <value>86400</value>
    </property>
    <property>
    <name>yarn.resourcemanager.webapp.address</name>
    <value>hadoop001:8088</value>
    </property>
    <property>
    <name>yarn.log.server.url</name>
    <value>http://hadoop001:19888/jobhistory/logs</value>
    </property>
    <property>
    <name>yarn.scheduler.maximum-allocation-mb</name>
    <value>6144</value>
    </property>
    <property>
    <name>yarn.scheduler.minimum-allocation-mb</name>
    <value>2048</value>
    </property>
    <property>
    <name>yarn.nodemanager.resource.memory-mb</name>
    <value>6144</value>
    </property>
    <property>
    <name>yarn.nodemanager.resource.cpu-vcores</name>
    <value>1</value>
    </property>
    <property>
    <name>mapreduce.map.memory.mb</name>
    <value>2048</value>
    </property>
    <property>
    <name>mapreduce.map.java.opts</name>
    <value>-Xmx1638m</value>
    </property>
    <property>
    <name>mapreduce.reduce.memory.mb</name>
    <value>4096</value>
    </property>
    <property>
    <name>mapreduce.reduce.java.opts</name>
    <value>-Xmx3276m</value>
    </property>
    <property>
    <name>yarn.app.mapreduce.am.resource.mb</name>
    <value>4096</value>
    </property>
    <property>
    <name>yarn.app.mapreduce.am.command-opts</name>
    <value>-Xmx3276m</value>
    </property>
    <property>
    <name>mapreduce.task.io.sort.mb</name>
    <value>819</value>
    </property>
    </configuration>
    ```
* 4G系统 建议yarn的内配置
    ```
    Using cores=1 memory=4GB disks=1 hbase=False
    Profile: cores=1 memory=3072MB reserved=1GB usableMem=3GB disks=1
    Num Container=3
    Container Ram=1024MB
    Used Ram=3GB
    Unused Ram=1GB
    yarn.scheduler.minimum-allocation-mb=1024
    yarn.scheduler.maximum-allocation-mb=3072
    yarn.nodemanager.resource.memory-mb=3072
    mapreduce.map.memory.mb=1024
    mapreduce.map.java.opts=-Xmx819m
    mapreduce.reduce.memory.mb=2048
    mapreduce.reduce.java.opts=-Xmx1638m
    yarn.app.mapreduce.am.resource.mb=2048
    yarn.app.mapreduce.am.command-opts=-Xmx1638m
    mapreduce.task.io.sort.mb=409
    ```

* Hadoop初始化并启动集群
```
# 初始化namenode datanode
$ bin/hdfs namenode -format

# 启动namenode datanode
$ sbin/start-dfs.sh
# 关闭namenode datanode
$ sbin/stop-dfs.sh

# 测试hdfs 和 MapReduce
$ bin/hdfs dfs -mkdir /input
$ bin/hdfs dfs -mkdir /test
$ bin/hdfs dfs -put etc/hadoop /input
$ bin/hadoop jar share/hadoop/mapreduce/hadoop-mapreduce-examples-2.7.6.jar grep $ input output 'dfs[a-z.]+'
# 查询hdfs 文件
方式一：
$ bin/hdfs dfs -get output output
$ cat output/*
方式二：
$ bin/hdfs dfs -cat output/*

#启动Yarn资源服务
$ sbin/start-yarn.sh

# 关闭 Yarn资源服务
$ sbin/stop-yarn.sh
```

* 端口号
```
Namenode：http://localhost:50070/
ResourceManager : http://localhost:8088/
```
8、 Hive配置 

* 安装mysql5.7
> 参照：https://www.cnblogs.com/wishwzp/p/7113403.html

* 在$HIVE_HOME/conf目录下hive-site.xml：
```
<configuration>
<property>
<name>javax.jdo.option.ConnectionURL</name>
<value>jdbc:mysql://192.168.101.92:3306/hive?createDatabaseIfNotExist=true&amp;characterEncoding=UTF-8</value>
</property>
<property>
<name>javax.jdo.option.ConnectionDriverName</name>
<value>com.mysql.jdbc.Driver</value>
</property>
<property>
<name>javax.jdo.option.ConnectionUserName</name>
<value>hive</value>
</property>
<property>
<name>javax.jdo.option.ConnectionPassword</name>
<value>hive</value>
</property>
</configuration>
```
* 配置jdbc的驱动

> 下载mysql-connector-java-5.1.30-bin.jar 包，复制放到/opt/hive/lib目录下就可以了

* 在hive2.0以后的版本，初始化hive指令
```
$ schematool -dbType mysql -initSchema

导数据创建表
# hive_data.txt
1,test01,23,address01
2,test02,45,address02
3,test03,8,addresss01

$ create table test(id string,name string ,addr string)  row format delimited fields terminated by ',';
$ LOAD DATA LOCAL INPATH '/home/hadoop/hive_data/hive_data.txt' INTO TABLE test;
```

9、 Spark配置
* 修改$SPARK_HOME/conf/spark-env.sh
```
JAVA_HOME=/opt/jdk1.8
HADOOP_HOME=/opt/hadoop-2.7.4
SCALA_HOME=/opt/scala-2.11.12
SPARK_HOME=/opt/spark-2.2.1-bin-hadoop2.7
HADOOP_CONF_DIR=/opt/hadoop-2.7.4/etc/hadoop
SPARK_HISTORY_OPTS="-Dspark.history.ui.port=18080 -Dspark.history.retainedApplications=100 -Dspark.history.fs.logDirectory=hdfs://hadoop001:9000/spark/historyLog"
```
* 修改$SPARK_HOME/conf/spark-defaults.conf
```
 spark.master                      yarn
 spark.deploy.mode                 cluster
 spark.yarn.historyServer.address  yarn1:18080
 spark.history.ui.port             18080
 spark.eventLog.enabled            true
 spark.eventLog.dir                hdfs://hadoop001:9000/spark/historyLog
 spark.history.fs.logDirectory     hdfs://hadoop001:9000/spark/historyLog
 spark.eventLog.compress           true
 spark.executor.instances          4
 spark.worker.cores                4
 spark.worker.memory               4G
```
10、 Flume配置
* 下载Flume
```
wget http://ftp.meisei-u.ac.jp/mirror/apache/dist/flume/1.8.0/apache-flume-1.8.0-bin.tar.gz
tar -zxvf apache-flume-1.8.0-bin.tar.gz
```
* 配置Flume环境变量/etc/profile
```
export FLUME_HOME=/opt/apache-flume-1.8.0-bin
export PATH=$PATH:$FLUME_HOME/bin
```
* Flume配置文件修改
```
$ cp flume-env.sh.template flume-env.sh
$ cp flume-conf.properties.template flume-conf.properties
$ vim flume-env.sh
export JAVA_HOME=/opt/jdk1.8
# 验证
$ flume-ng version
```

* 案例一
    * 增加配置文件example.conf，$FLUME_HOME/conf/example.conf
    ```
    # example.conf: A single-node Flume configuration
    
     # Name the components on this agent
     a1.sources = r1
     a1.sinks = k1
     a1.channels = c1
    
     # Describe/configure the source
     a1.sources.r1.type = netcat
    # a1.sources.r1.bind = 172.27.3.201
     a1.sources.r1.bind = localhost
     a1.sources.r1.port = 44444
    
     # Describe the sink
     a1.sinks.k1.type = logger
    
     # Use a channel which buffers events in memory
     a1.channels.c1.type = memory
     a1.channels.c1.capacity = 1000
     a1.channels.c1.transactionCapacity = 100
    ```

    * 启动服务
    ```
    $ flume-ng agent --conf conf --conf-file example.conf --name a1 -Dflume.root.logger=INFO,console
    ```
    
    * Client发送Message
    ```
    $ telnet localhost 44444
    ```

11、 Flume Spool用于监测配置的目录下新增的文件，并将文件中的数据读取出来

* 准备数据文件
```
$ mkdir -p /root/avro_data
$ cd /root/avro_data
$ vim avro_data.txt
1,test01,23,address01
2,test02,45,address02
3,test03,8,addresss01
```
* 准备数据文件
    * spool1.conf
    ```
     # Name the components on this agent
    #agent名， source、channel、sink的名称
    
     a1.sources = r1
    
     a1.channels = c1
    
     a1.sinks = k1
    
    ##具体定义source
    
     a1.sources.r1.type = spooldir
    
     a1.sources.r1.spoolDir = /home/hadoop/avro_data/
    
    #具体定义channel
    
     a1.channels.c1.type = memory
    
     a1.channels.c1.capacity = 10000
    
     a1.channels.c1.transactionCapacity = 100
    
    ##具体定义sink
    
     a1.sinks.k1.type = hdfs
    
     a1.sinks.k1.hdfs.path = hdfs://hadoop001:9000/flume/%Y%m%d
    
     a1.sinks.k1.hdfs.filePrefix = events-
    
     a1.sinks.k1.hdfs.fileType = DataStream
    
     a1.sinks.k1.hdfs.useLocalTimeStamp = true
    
    #不按照条数生成文件
    
     a1.sinks.k1.hdfs.rollCount = 0
    
    #HDFS上的文件达到128M时生成一个文件
    
     a1.sinks.k1.hdfs.rollSize = 134217728
    
    #HDFS上的文件达到60秒生成一个文件
    
     a1.sinks.k1.hdfs.rollInterval = 60
    
     #组装source、channel、sink
    
     a1.sources.r1.channels = c1
    
     a1.sinks.k1.channel = c1
    ```
    
    * demo运行指令
    ```
    # 启动服务
    $ flume-ng agent -c conf -f ./spool2.conf -n a1
    # 新开一个窗口，传输数据
    $ cp /home/hadoop/avro_data/avro_data.txt.COMPLETED /home/hadoop/avro_data/avro_data04.txt
    ```

12、 Sqoop安装
* 配置环境变
```
export SQOOP_HOME=/opt/sqoop-1.4.5
export PATH=$PATH:$SQOOP_HOME/bin
```
* 拷贝mysql驱动包到/opt/sqoop-1.4.5/lib下： 
```
$ cp /opt/software/mysql-connector-java-5.1.27-bin.jar /opt/sqoop-1.4.5/lib
```
* hive数据导入mysql
    * 进入Mysql数据库创建表
    ```
    create table test(id varchar(32),name varchar(32),addr varchar(32));
    ```

    * 导入指令
        * [sqoop-1.4.5.jar下载地址](http://archive.apache.org/dist/sqoop/1.4.5/sqoop-1.4.5.bin__hadoop-2.0.4-alpha.tar.gz)
        > 把sqoop-1.4.7.jar copy到 lib下
    * 执行指令
    ```
    $ sqoop export --connect jdbc:mysql://localhost:3306/test?characterEncoding=utf-8 --username root --password root --table test --export-dir /user/hive/warehouse/test/  --input-fields-terminated-by ','
    ```
