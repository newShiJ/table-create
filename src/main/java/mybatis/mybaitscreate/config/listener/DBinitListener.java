package mybatis.mybaitscreate.config.listener;

import mybatis.mybaitscreate.initDB.ColumnDescript;
import mybatis.mybaitscreate.initDB.TableBuilder;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author chenmingming
 * @date 2018/9/4
 */
public class DBinitListener implements ApplicationRunner ,Ordered {

    private final DataSource dataSource;

    private final String packageName;

    public DBinitListener(DataSource dataSource, String packageName) {
        this.dataSource = dataSource;
        this.packageName = packageName;
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming application arguments
     * @throws Exception on error
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(true);
        List<Class> classes = classes(packageName);
        for (Class aClass : classes) {
            init(connection, aClass);
        }


        connection.close();
    }

    private void init(Connection connection, Class clazz) throws Exception {
        TableBuilder builder = new TableBuilder(clazz);
        String tableName = builder.tableName();

        //1 首先判断数据库表是否存在 决定了是 create 还是 alter
        boolean exists = false;
        String existsTable = String.format("show tables like '%s'", tableName);
        PreparedStatement existsStat = connection.prepareStatement(existsTable);
        ResultSet query = existsStat.executeQuery();
        exists = query.next();

        //2 不存在表执行 create
        if (!exists) {
            String ddl = builder.DDL();
            PreparedStatement statement = connection.prepareStatement(ddl);
            statement.execute();

        }
        //3 存在表执行 alter
        else {
            String alter = String.format("desc %s", tableName);
            PreparedStatement columns = connection.prepareStatement(alter);
            ResultSet resultSet = columns.executeQuery();
            List<String> fields = new ArrayList<>();
            List<ColumnDescript> descriptList = builder.columnDescriptList();
            while (resultSet.next()) {
                String field = resultSet.getString("FIELD");
                fields.add(field);
            }
            Iterator<ColumnDescript> iterator = descriptList.iterator();
            while (iterator.hasNext()) {
                ColumnDescript next = iterator.next();
                String name = next.columnName();
                if (fields.indexOf(name) > -1) {
                    iterator.remove();
                }
            }
            if(descriptList.size()>0){
                String alterStr = builder.ALTER(descriptList);
                PreparedStatement statement = connection.prepareStatement(alterStr);
                statement.execute();
            }
        }
    }


    private static List<Class> classes(String packeageName)throws Exception{
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        String replace = packeageName.replace(".", "/");
        replace += "/*";
        Resource[] resources = resolver.getResources(replace);
        List<Class> list = new ArrayList<>();
        for (Resource resource : resources) {
            String description = resource.getFilename();
            //String description = resource.getDescription();
            String[] split = description.split("/");
            String name = split[split.length - 1];
            String[] split1 = name.split("\\.");
            name = split1[0];
            name = packeageName+ "." + name;
           list.add(Class.forName(name));
        }
        return list;
    }

    /**
     * Get the order value of this object.
     * <p>Higher values are interpreted as lower priority. As a consequence,
     * the object with the lowest value has the highest priority (somewhat
     * analogous to Servlet {@code load-on-startup} values).
     * <p>Same order values will result in arbitrary sort positions for the
     * affected objects.
     *
     * @return the order value
     * @see #HIGHEST_PRECEDENCE
     * @see #LOWEST_PRECEDENCE
     */
    @Override
    public int getOrder() {
        return 10;
    }
}















