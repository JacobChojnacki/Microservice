package com.zpo.project.safephoto.infrastructure.services;

import com.google.gson.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.zpo.project.safephoto.domain.Metadata;
import com.zpo.project.safephoto.domain.projections.MetadataProjection;
import com.zpo.project.safephoto.domain.snapshots.MetadataSnapshot;
import com.zpo.project.safephoto.infrastructure.interfaces.ICustomerService;
import com.zpo.project.safephoto.infrastructure.models.CustomerModel;
import com.zpo.project.safephoto.infrastructure.models.MetadataModel;
import com.zpo.project.safephoto.infrastructure.models.PhotoModel;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class CustomerJsonService implements ICustomerService {
    @Override
    public CustomerModel parseFromString(String customerString) {
        var gson = new Gson();
        return gson.fromJson(customerString, CustomerModel.class);
    }

    @Override
    public PhotoModel parsePhotoFromString(String photoString) {
        var gson = new Gson();
        return gson.fromJson(photoString, PhotoModel.class);
    }

    @Override
    public MetadataModel parseMetadataFromString(String metadataString) {
        var gson = new Gson();
        return gson.fromJson(metadataString, MetadataModel.class);
    }

    @Override
    public String serializeToString(Metadata metadata) {
        Gson gson = new GsonBuilder().registerTypeAdapter(ZonedDateTime.class, new JsonSerializer<ZonedDateTime>() {

            @Override
            public JsonElement serialize(ZonedDateTime zonedDateTime, Type srcType, JsonSerializationContext context) {
                return new JsonPrimitive(zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")));
            }
        }).create();
        return gson.toJson(metadata);
    }

    @Override
    public String serializeToString(MetadataModel metadataModel) {
        var gson = new Gson();
        return gson.toJson(metadataModel);
    }

    @Override
    public String serializeToString(PhotoModel photoModel) {
        var gson = new Gson();
        return gson.toJson(photoModel);
    }

    @Override
    public MetadataSnapshot parseMetadataSnapshotFromString(String metadataSnapshot) {
        Gson gson = new GsonBuilder().registerTypeAdapter(ZonedDateTime.class, new JsonDeserializer<ZonedDateTime>() {
            @Override
            public ZonedDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));
            }
        }).create();
        return gson.fromJson(metadataSnapshot, MetadataSnapshot.class);
    }

    @Override
    public MetadataProjection parseMetadataProjectionFromString(String metadataProjection) {
        Gson gson = new GsonBuilder().registerTypeAdapter(ZonedDateTime.class, new JsonDeserializer<ZonedDateTime>() {
            @Override
            public ZonedDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));
            }
        }).create();
        return gson.fromJson(metadataProjection, MetadataProjection.class);
    }
}
