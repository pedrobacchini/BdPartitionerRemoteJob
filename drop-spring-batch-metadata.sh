CONTAINER='mysqlsrv'
docker exec $CONTAINER mysql -u root --password='123456' -D spring_batch --execute="DROP TABLE IF EXISTS BATCH_STEP_EXECUTION_CONTEXT ;
                                                                                                  DROP TABLE IF EXISTS BATCH_JOB_EXECUTION_CONTEXT ;
                                                                                                  DROP TABLE IF EXISTS BATCH_STEP_EXECUTION ;
                                                                                                  DROP TABLE IF EXISTS BATCH_JOB_EXECUTION_PARAMS ;
                                                                                                  DROP TABLE IF EXISTS BATCH_JOB_EXECUTION ;
                                                                                                  DROP TABLE IF EXISTS BATCH_JOB_INSTANCE ;

                                                                                                  DROP TABLE IF EXISTS BATCH_STEP_EXECUTION_SEQ ;
                                                                                                  DROP TABLE IF EXISTS BATCH_JOB_EXECUTION_SEQ ;
                                                                                                  DROP TABLE IF EXISTS BATCH_JOB_SEQ ;
                                                                                                  DROP TABLE IF EXISTS flyway_schema_history"