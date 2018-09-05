package mybatis.mybaitscreate.initDB;

import java.lang.reflect.Field;

/**
 * @author chenmingming
 * @date 2018/9/4
 */
public class VarcharColumnDescript extends ColumnDescript {

    VarcharColumnDescript(Field field){
        super(field);
        this.type = "varchar";
    }
}
