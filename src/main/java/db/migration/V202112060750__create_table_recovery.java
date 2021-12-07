package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import static org.jooq.impl.DSL.*;
import static org.jooq.impl.SQLDataType.*;

public class V202112060750__create_table_recovery extends BaseJavaMigration {
    public void migrate(Context context) throws Exception {
        var create = using(context.getConnection());        
        create.transaction(configuration -> using(configuration)
            .createTableIfNotExists("recovery")
                .column("id", BIGINT.identity(true))
                .column("code", VARCHAR(4).nullable(false))
                .column("expires_in", TIMESTAMP.nullable(false))
                .column("user_id", BIGINT.nullable(false))
            .constraints(
                primaryKey("id"),
                unique("code"),
                foreignKey("user_id").references("user", "id"))
            .execute());
    }
}
