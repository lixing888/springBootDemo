package com.springboot.demo;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

/**
 * mybatis-plus 自动生成类
 */
public class GeneratorMybatisPlus {

    @Test
    public void generateActivityCode() {
        String packageName = "com.springboot.demo.store";
        boolean serviceNameStartWithI = false;//user -> UserService, 设置成true: user -> IUserService

        String dbUrl = "jdbc:mysql://139.129.45.208:3306/hboa?serverTimezone=UTC";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig
                .setUrl(dbUrl)
                .setUsername("root")
                .setTypeConvert(new MySqlTypeConvert())
                .setPassword("beiXINzhiKE")
                .setDriverName("com.mysql.jdbc.Driver");

        generateByTables("",serviceNameStartWithI,dataSourceConfig, packageName, "jc_user\\w+");
    }

    @Ignore
    @Test
    public void generateStatCode() {
        String packageName = "com.fy.ams.api";
        boolean serviceNameStartWithI = false;//user -> UserService, 设置成true: user -> IUserService

        String dbUrl = "jdbc:mysql://101.37.81.28:3307/stat";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig
                .setUrl(dbUrl)
                .setUsername("stat")
                .setPassword("Feiyustat2017")
                .setTypeConvert(new MySqlTypeConvert())
                .setDriverName("com.mysql.jdbc.Driver");

        generateByTables("stat",serviceNameStartWithI,dataSourceConfig, packageName, "stat2_robot_room_group_day", "stat2_robot_room_detail", "stat2_robot_player_winner_day", "stat2_player_game_detail");
    }


    private void generateByTables(String subPackage,boolean serviceNameStartWithI, DataSourceConfig dataSourceConfig, String packageName, String... tableNames) {


        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setEntityBooleanColumnRemoveIsPrefix(true)
                .setCapitalMode(true)
                .setEntityLombokModel(true)
                .setLogicDeleteFieldName("deleted")
                .setNaming(NamingStrategy.underline_to_camel)
                .setInclude(tableNames);//修改替换成你需要的表名，多个表名传数组
        GlobalConfig config = new GlobalConfig();
        config.setActiveRecord(false)
                .setDateType(DateType.ONLY_DATE)
                .setAuthor("zhaojingbo")
                .setOutputDir(System.getProperty("user.dir")+File.separator+"src"+File.separator+"main"+File.separator+"java")
                .setFileOverride(true)/*.setBaseColumnList(true).setBaseResultMap(true)*/
                .setOpen(false)
        ;

        // .setFileOverride(true);
        if (!serviceNameStartWithI) {
            config.setServiceName("%sManager");
            config.setServiceImplName("%sManagerImpl");
        }
        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(
                        new PackageConfig()
                                .setParent(packageName)
                                .setMapper("mapper")
                                .setXml("mapper")
                                .setEntity("entity")
                                .setService("manager")
                                .setServiceImpl("manager.impl")
                                .setController("controller.genertor")
                        // .setMapper("mapper.activity")
                        // .setXml("mapper.activity")
                        // .setEntity("entity.domain.activity")
                ).execute();

    }


    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
    }
}
