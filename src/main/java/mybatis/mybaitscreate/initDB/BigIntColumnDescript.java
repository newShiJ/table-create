package mybatis.mybaitscreate.initDB;

import java.lang.reflect.Field;

/**
 * @author chenmingming
 * @date 2018/9/4
 */
public class BigIntColumnDescript extends NoLengthColumnDescript {
    BigIntColumnDescript(Field field) {
        super(field);
        this.type = "bigint";
    }
}
