package updater;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.spectralsolutions.elementupdater.action.ActionExtractJar;
import com.spectralsolutions.elementupdater.objects.MongoConfig;
import com.spectralsolutions.elementupdater.objects.UpdateArgs;
import com.spectralsolutions.elementupdater.storage.LocalStorageXML;
import com.spectralsolutions.elementupdater.updater.UpdateWithMongo;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Tius on 12/8/2016.
 */
public class UpdateWithMongoTest {
    @Before
    public void Setup()
    {
        MongoConfig config = MongoConfig.PROD;
        MongoClient mongoClient = new MongoClient(config.getPrimaryMongoURI());
        DB database = mongoClient.getDB(config.getDatabase());
        //Jongo jongo = new Jongo(database);
        //jongo.getCollection("updates").save(new UpdateArgs("1.8.8","http://update.com/updates"));
    }

    @Test
    public void ConnectionToMongoIsMade()
    {
        UpdateWithMongo uwm = new UpdateWithMongo(new ActionExtractJar("user.dir"), new LocalStorageXML("user.dir"));
        UpdateArgs a = uwm.GetUpdateArgs();
        Assert.assertTrue( a != null);
    }

    @After
    public void TearDown()
    {
        MongoConfig config = MongoConfig.PROD;
        MongoClient mongoClient = new MongoClient(config.getPrimaryMongoURI());
        DB database = mongoClient.getDB(config.getDatabase());
        Jongo jongo = new Jongo(database);
        UpdateArgs a = jongo.getCollection("updates").findOne("{}").as(UpdateArgs.class);
        jongo.getCollection("updates").remove("{_id: #}", new ObjectId(a._id));
    }

}
