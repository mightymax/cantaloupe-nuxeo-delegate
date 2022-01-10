package io.memorix.cantaloupe;

import edu.illinois.library.cantaloupe.config.Configuration;
import edu.illinois.library.cantaloupe.delegate.AbstractJavaDelegate;
import edu.illinois.library.cantaloupe.delegate.JavaDelegate;
import edu.illinois.library.cantaloupe.delegate.Logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CantaloupeNuxeoDelegate extends AbstractJavaDelegate implements JavaDelegate {

    protected Configuration config = Configuration.getInstance();
    final NuxeoClient nuxeoClient = new NuxeoClient();

    @Override
    public Boolean preAuthorize() {

      return true;
    }

    @Override
    public Boolean authorize() {
        return true;
    }

    @Override
    public Map<String,Object> getHTTPSourceResourceInfo() {
      Map<String,Object> resource = new HashMap<>();
      resource.put("uri", nuxeoClient.getFileUrlForIdentifier(getContext().getIdentifier()));
      resource.put("username", config.getString(NuxeoKey.NX_USERNAME.key()));
      resource.put("secret", config.getString(NuxeoKey.NX_SECRET.key()));
      return resource;
    }

    @Override
    public Map<String,Object> getExtraIIIF2InformationResponseKeys() {
      return getExtraIIIF3InformationResponseKeys();
    }

    @Override
    public Map<String,Object> getExtraIIIF3InformationResponseKeys() {
      if (!config.getBoolean(NuxeoKey.NX_LOAD_PROPERTIES.key(), false)) return Collections.emptyMap();
      else return nuxeoClient.getMetadata(getContext().getIdentifier());
    }

    @Override
    public String getSource() {
      //NB this will only be used when source.delegate = true
      return "HttpSource";
    }

    @Override
    public String getMetadata() {
      Logger.debug(getContext().getMetadata().toString());
      return null;
    }

    @Override
    public String getAzureStorageSourceBlobKey() {
      return null;
    }

    @Override
    public String getFilesystemSourcePathname() {
      return null;
    }

    @Override
    public String getJDBCSourceDatabaseIdentifier() {
      return null;
    }

    @Override
    public String getJDBCSourceMediaType() {
      return null;
    }

    @Override
    public String getJDBCSourceLookupSQL() {
      return null;
    }

    @Override
    public Map<String,String> getS3SourceObjectInfo() {
      return null;
    }

    @Override
    public Map<String,Object> getOverlay() {
      return null;
    }

    @Override
    public List<Map<String,Long>> getRedactions() {
      return Collections.emptyList();
    }

    @Override
    public String serializeMetaIdentifier(Map<String,Object> metaIdentifier) {
        return null;
    }

    @Override
    public Map<String,Object> deserializeMetaIdentifier(String metaIdentifier) {
        return null;
    }

}
