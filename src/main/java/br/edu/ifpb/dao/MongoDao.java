package br.edu.ifpb.dao;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

public class MongoDao {

    private DB database;
    private Gson gson = new Gson();

    public MongoDao(){
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        database = mongoClient.getDB("engenharia_software");

    }

    public void insert(Object object){
        DBCollection collection = database.getCollection(object.getClass().getSimpleName().toLowerCase());
        BasicDBObject obj = (BasicDBObject) JSON.parse(gson.toJson(object));
        collection.insert(obj);
    }
}
