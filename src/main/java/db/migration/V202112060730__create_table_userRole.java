package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import static org.jooq.impl.DSL.foreignKey;
import static org.jooq.impl.DSL.using;
import static org.jooq.impl.SQLDataType.BIGINT;

public class V202112060730__create_table_userRole extends BaseJavaMigration {
    public void migrate(Context context) throws Exception {
        var create = using(context.getConnection());
        create.transaction(configuration -> using(configuration)
            .createTableIfNotExists("user_role")
                .column("user_id", BIGINT.nullable(true))
                .column("role_id", BIGINT.nullable(true))
            .constraints(
                foreignKey("user_id").references("user", "id"),
                foreignKey("role_id").references("role", "id"))
            .execute());
    }
}
