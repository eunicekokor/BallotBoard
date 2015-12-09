import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.*;

import play.Application;
import play.GlobalSettings;
import play.mvc.*;
import play.test.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;
import play.twirl.api.Content;

import static play.test.Helpers.*;
import static org.junit.Assert.*;

import models.*;
import models.Ballot;

import play.api.Play;
import play.api.test.FakeApplication;

public class ViewTest {

    private Ballot _ballot1 = new Ballot();
    private Ballot _ballot2 = new Ballot();

    List<Ballot> ballotList = new ArrayList<Ballot>();

    @Test

    public void renderBallot(){
      Content html = views.html.ballot.render(ballotList, "BALLOT");
      assertEquals(contentType(html), "text/html");
      assertThat(contentAsString(html)).contains("BALLOT");
    }

        @Test

    public void renderBallotView(){
      Content html = views.html.ballotView.render(_ballot1, "Eunice", "message Eunice");
      assertEquals(contentType(html), "text/html");
      // System.out.println(contentAsString(html));
      assertThat(contentAsString(html)).contains("Eunice");
    }

}
