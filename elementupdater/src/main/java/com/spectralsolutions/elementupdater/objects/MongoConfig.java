package com.spectralsolutions.elementupdater.objects;


import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;

/**
 * Description:
 * The DBMode enumeration is used to specify
 * if the database is running in online or
 * local mode.
 *
 * PROD - connected to the production server
 * TEST - connected to the test server
 * DEV - connected to the dev server
 * LOCAL - connected to the local server
 *
 * @author Rosh Jayawardena - Spectral Solutions ltd. Created 04 Oct 2016
 */
public enum MongoConfig {
    PROD("spectralelementproduction", "specprod:ExqZLMpKiTYmFW6AuXpl@13.77.2.86:27017", "specprod:ExqZLMpKiTYmFW6AuXpl@13.70.184.111:27017"),
    /*TEST("","",""),*/
    DEV("spectralelementdev", "dbuser:U1P7-7OBDV95ACODVW7DJIY4FO831VD6IOI92L5QXRM_5RO7H_LQXQEZ1DUN85YA@40.126.229.164:27017", "dbuser:U1P7-7OBDV95ACODVW7DJIY4FO831VD6IOI92L5QXRM_5RO7H_LQXQEZ1DUN85YA@40.126.229.164:27017"),
    LOCAL("spectralelementproduction", "localhost:27017", "localhost:27017");

    private final String database;
    private MongoClientURI PrimaryMongoURI;
    private MongoClientURI SecondaryMongoURI;


    MongoConfig(String database, String primary, String secondary) {
        if (database.isEmpty() || primary.isEmpty() || secondary.isEmpty()) {
            throw new IllegalArgumentException("DBMode arguments are null");
        }
        this.database = database;
        PrimaryMongoURI = new MongoClientURI(ConcatDatabase(primary), configureMongoClient().builder());
        SecondaryMongoURI = new MongoClientURI(ConcatDatabase(secondary), configureMongoClient().builder());
    }

    MongoConfig(String database, String primary, String secondary, boolean ssl)//
    {
        if (database.isEmpty() || primary.isEmpty()) {
            throw new IllegalArgumentException("DBMode arguments are null");
        }
        this.database = database;
        if (ssl) {
            PrimaryMongoURI = new MongoClientURI(ConcatDatabase(primary), configureMongoClient(true).builder());
            SecondaryMongoURI = new MongoClientURI(ConcatDatabase(secondary), configureMongoClient(true).builder());
        } else {
            PrimaryMongoURI = new MongoClientURI(ConcatDatabase(primary), configureMongoClient().builder());
            SecondaryMongoURI = new MongoClientURI(ConcatDatabase(secondary), configureMongoClient().builder());
        }
    }


    /**
     * Description:
     * Helper function to format the connection string to include the database
     *
     * @param connectiondetails A formatted string containing authentication information and the target server and port
     * @return String A formatted Mongo connection string
     */
    private String ConcatDatabase(String connectiondetails) {
        String temp = "mongodb://".concat(connectiondetails.concat("/".concat(this.database + "?authMechanism=SCRAM-SHA-1")));//lazy concat to format connection string with database
        return temp;
    }

    /**
     * Description:
     * Prepares an option configuration to be used when instantiating a MongoClient
     *
     * @return MongoClientOptions
     */
    private MongoClientOptions configureMongoClient() {
        //logger.info("Mongoclient options are being called");

        MongoClientOptions returnValue = null;

        MongoClientOptions options = MongoClientOptions.builder()
                .socketKeepAlive(true)
                .connectionsPerHost(40)
                .connectTimeout(3000)
                .socketTimeout(60000)
                .threadsAllowedToBlockForConnectionMultiplier(20)
                .build();
        //this will set up the required options

        returnValue = options;

        //logger.info("Mongoclient options were successfully generated");

        return returnValue;
    }

    /**
     * Description:
     * Prepares an option configuration to be used when instantiating a MongoClient
     *
     * @param ssl boolean value to connect using SSL
     * @return MongoClientOptions
     */
    private MongoClientOptions configureMongoClient(boolean ssl) {
        //logger.info("Mongoclient options are being called");

        MongoClientOptions returnValue = null;

        MongoClientOptions options = MongoClientOptions.builder()
                .sslEnabled(ssl)
                .socketKeepAlive(true)
                .connectionsPerHost(40)
                .connectTimeout(3000)
                .socketTimeout(60000)
                .threadsAllowedToBlockForConnectionMultiplier(20)
                .build();
        //this will set up the required options

        returnValue = options;

        //logger.info("Mongoclient options were successfully generated");

        return returnValue;
    }


    public MongoClientURI getPrimaryMongoURI() {
        return PrimaryMongoURI;
    }

    public MongoClientURI getSecondaryMongoURI() {
        return SecondaryMongoURI;
    }

    /**
     * Description:
     * Database name read only property
     *
     * @return String current connected database name
     */
    public String getDatabase() {
        return database;
    }
}


