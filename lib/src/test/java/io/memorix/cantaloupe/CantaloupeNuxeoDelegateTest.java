package io.memorix.cantaloupe;

import org.junit.jupiter.api.Test;

import edu.illinois.library.cantaloupe.config.Configuration;
import edu.illinois.library.cantaloupe.delegate.JavaRequestContext;
import edu.illinois.library.cantaloupe.image.Identifier;
import edu.illinois.library.cantaloupe.resource.RequestContext;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.BeforeAll;

class CantaloupeNuxeoDelegateTest extends BaseTest
{

  private static CantaloupeNuxeoDelegate delegate;

  public static CantaloupeNuxeoDelegate getDelegate() {
    if (null == delegate) {
      delegate = new CantaloupeNuxeoDelegate();
      final RequestContext requestContext = new RequestContext();
      requestContext.setIdentifier(new Identifier("cats"));
      final JavaRequestContext context = new JavaRequestContext(requestContext);
      delegate.setContext(context);
    }
    return delegate;
  }

  @BeforeAll
  static public void setUp() throws Exception {
    BaseTest.setUp();
  }


  @Test
  void testResource()
  {
    
    CantaloupeNuxeoDelegate delegate = getDelegate();
    Map<String,Object> resource = delegate.getHTTPSourceResourceInfo();

    assertTrue(resource.containsKey("uri"), "Missing key `uri` in response resource");
    assertTrue(resource.containsKey("username"), "Missing key `username` in response resource");
    assertTrue(resource.containsKey("secret"), "Missing key `secret` in response resource");


    assertTrue(
      resource.get("uri").toString().startsWith(
        Configuration.getInstance().getString(NuxeoKey.NX_URL.key())
      ), "uri is not correct"
    );

    assertEquals(NX_USERNAME, resource.get("username"), "Wrong value for key `username`");
    assertEquals(NX_SECRET, resource.get("secret"), "Wrong value for key `secret`");
  }

  @Test void testAuthorization()
  {

  }
}
