package my.studying.networking.githubclient_v1;

import org.junit.Test;
import static org.junit.Assert.*;

import com.google.gson.Gson;

import my.studying.networking.githubclient_v1.data.model.Repository;
import my.studying.networking.githubclient_v1.util.Resource;

public class RequirementsUnitTest {

    @Test
    public void testRepositoryParsingNonFork() {
        String json = "{\n" +
                "  \"name\": \"guava\",\n" +
                "  \"full_name\": \"google/guava\",\n" +
                "  \"description\": \"Google core libraries for Java\",\n" +
                "  \"fork\": false,\n" +
                "  \"forks_count\": 5000,\n" +
                "  \"watchers_count\": 25000,\n" +
                "  \"open_issues_count\": 300\n" +
                "}";

        Gson gson = new Gson();
        Repository repo = gson.fromJson(json, Repository.class);

        assertNotNull(repo);
        assertEquals("guava", repo.getName());
        assertEquals("google/guava", repo.getFullName());
        assertEquals("Google core libraries for Java", repo.getDescription());
        assertFalse(repo.isFork());
        assertEquals(5000, repo.getForksCount());
        assertEquals(25000, repo.getWatchersCount());
        assertEquals(300, repo.getOpenIssuesCount());
        assertNull(repo.getParent());
    }

    @Test
    public void testRepositoryParsingForkWithParent() {
        String json = "{\n" +
                "  \"name\": \"guava\",\n" +
                "  \"full_name\": \"myorg/guava\",\n" +
                "  \"description\": \"\",\n" +
                "  \"fork\": true,\n" +
                "  \"forks_count\": 10,\n" +
                "  \"watchers_count\": 50,\n" +
                "  \"open_issues_count\": 2,\n" +
                "  \"parent\": {\n" +
                "    \"full_name\": \"google/guava\"\n" +
                "  }\n" +
                "}";

        Gson gson = new Gson();
        Repository repo = gson.fromJson(json, Repository.class);

        assertNotNull(repo);
        assertEquals("guava", repo.getName());
        assertTrue(repo.isFork());
        assertNotNull(repo.getParent());
        assertEquals("google/guava", repo.getParent().getFullName());
    }

    @Test
    public void testResourceWrapperAndErrorMessageFormat() {
        Resource<String> loadingRes = Resource.loading();
        assertEquals(Resource.Status.LOADING, loadingRes.getStatus());
        assertNull(loadingRes.getData());

        Resource<String> successRes = Resource.success("data");
        assertEquals(Resource.Status.SUCCESS, successRes.getStatus());
        assertEquals("data", successRes.getData());

        String orgName = "google";
        Resource<String> errorRes = Resource.error(orgName);
        assertEquals(Resource.Status.ERROR, errorRes.getStatus());
        assertEquals(orgName, errorRes.getMessage());

        String expectedToastText = "No repos found for organization " + errorRes.getMessage();
        assertEquals("No repos found for organization google", expectedToastText);
    }
}
