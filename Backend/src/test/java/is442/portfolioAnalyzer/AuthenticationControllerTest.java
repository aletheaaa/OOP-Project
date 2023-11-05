package is442.portfolioAnalyzer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import is442.portfolioAnalyzer.Asset.Asset;
import is442.portfolioAnalyzer.JsonModels.PortfolioCreation;
import is442.portfolioAnalyzer.JsonModels.PortfolioUpdate;
import is442.portfolioAnalyzer.JsonModels.AssetCreation;
import is442.portfolioAnalyzer.JsonModels.PortfolioCreation;
import is442.portfolioAnalyzer.auth.AuthenticationController;
import is442.portfolioAnalyzer.auth.AuthenticationRequest;
import is442.portfolioAnalyzer.auth.AuthenticationService;
import is442.portfolioAnalyzer.auth.AuthenticationResponse;
import is442.portfolioAnalyzer.auth.RegisterRequest;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import is442.portfolioAnalyzer.JsonModels.PortfolioCreation;
import is442.portfolioAnalyzer.Portfolio.PortfolioController;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
@SpringJUnitConfig
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class AuthenticationControllerTest {
    private static String token;
    private static int id;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AuthenticationService service;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @Order(1)
    public void testRegister() throws Exception {
        // Create a RegisterRequest object (customize with registration data)
        RegisterRequest registerRequest = new RegisterRequest();
        // registerRequest.setUsername("testuser");
        registerRequest.setEmail("testuser5@email.com");
        registerRequest.setFirstname("test");
        registerRequest.setLastname("user5");
        registerRequest.setPassword("P@$$w0rd");
        // Serialize the object to JSON
        String registerRequestJson = new ObjectMapper().writeValueAsString(registerRequest);

        // Perform a POST request to the register endpoint
        ResultActions result = mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerRequestJson))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        // Extract the content from the response
        String responseContent = result.andReturn().getResponse().getContentAsString();

        // Parse the content to an object using ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        AuthenticationResponse response = objectMapper.readValue(responseContent, AuthenticationResponse.class);

        // Get the "token" from the parsed object
        token = response.getToken();
        // token = token.substring(7);
        // token = "-------"+ token;
        id = response.getId();
        System.out.println("This is the user's id in register "+ id);
        System.out.println("This is the user's token in register"+ token);
        
    }

    @Test
    @Order(2)
    public void testCreatePortfolio() throws Exception {
        // Assign the values of the global variables to the local variables
        int id2 = id;
        String token2 = token;
        // ...
        System.out.println("this is userid in create "+id2);
        System.out.println("this is user token in create "+token2);
        // Create a PortfolioCreation object (you can customize the data)
        PortfolioCreation portfolioCreation = new PortfolioCreation(0, null, null, null, null, null, null);
        portfolioCreation.setPortfolioName("testing portfolio");
        portfolioCreation.setDescription("A test portfolio");
        portfolioCreation.setCapital(10000);
        portfolioCreation.setTimePeriod("Monthly");
        portfolioCreation.setUserId(id);
        portfolioCreation.setStartDate("2000-01");

        List<AssetCreation> assetList = new ArrayList<>();
        assetList.add(new AssetCreation("AAPL", 0.5));
        assetList.add(new AssetCreation("PFE", 0.3));
        assetList.add(new AssetCreation("AAIC", 0.2));
        portfolioCreation.setAssetList(assetList);

        // Serialize the object to JSON
        String portfolioCreationJson = new ObjectMapper().writeValueAsString(portfolioCreation);
        System.out.println(portfolioCreationJson);

        // System.out.println("this is userid second "+id);
        // System.out.println("this is user token second "+token);
         // Pause for 10 seconds
        Thread.sleep(10000);
        // Perform a POST request to the createPortfolio endpoint
        mockMvc.perform(post("/portfolio/createPortfolio/{userId}", id2) // Replace 1 with the actual user ID
                .contentType(MediaType.APPLICATION_JSON)
                .content(portfolioCreationJson)
                .header("Authorization", "Bearer " + token2)) // Replace with a valid auth token
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
// @Ignore
// @Test
// public void testGetPortfolioDetails() throws Exception {
//     // input your test data here
//     int id2 = 152 ;
//     String token2 = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlcjVAZW1haWwuY29tIiwiaWF0IjoxNjk5MTc1MzczLCJleHAiOjE2OTkyNjE3NzN9.nrHJcncatbHdvAwxPcCeZVDfF4y341f1fipd-pdrMv0";
//     int portfolioid = 6; //manual

//    // Perform a GET request to the getPortfolioDetails endpoint
// mockMvc.perform(get("/portfolio/getPortfolioDetails/{userId}/{portfolioId}", id2, portfolioid)
//     .header("Authorization", "Bearer " + token2))
//     .andExpect(status().isOk())
//     .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//     .andExpect(jsonPath("$.portfolioName").value("testing portfolio"))
//     .andExpect(jsonPath("$.description").value("A test portfolio"))
//     .andExpect(jsonPath("$.capital").value(10000))
//     .andExpect(jsonPath("$.timePeriod").value("Monthly"))
//     .andExpect(jsonPath("$.startDate").value("2010-01"))
//     .andExpect(jsonPath("$.assetList[0].symbol").value("AAPL"))
//     .andExpect(jsonPath("$.assetList[0].weight").value(0.5))
//     .andExpect(jsonPath("$.assetList[1].symbol").value("PFE"))
//     .andExpect(jsonPath("$.assetList[1].weight").value(0.3))
//     .andExpect(jsonPath("$.assetList[2].symbol").value("AAIC"))
//     .andExpect(jsonPath("$.assetList[2].weight").value(0.2))
//     .andDo(MockMvcResultHandlers.print());
// }
    

@Ignore
@Test
public void testUpdatePortfolio() throws Exception {
    // input your test data here
    int id2 = 1;
    String token2 = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlcjFAZW1haWwuY29tIiwiaWF0IjoxNjk5MTczNzAzLCJleHAiOjE2OTkyNjAxMDN9.6_8NHFXzBYKDw9XUi1SmT7j4IuQpaD1TaFAdo5J8_00";
    int portfolioid = 1;
   
    PortfolioUpdate portfolioUpdate = new PortfolioUpdate(0,0,null,null,null,null,0,null);
    portfolioUpdate.setPortfolioName("updated portfolio");
    portfolioUpdate.setDescription("An updated test portfolio");
    portfolioUpdate.setCapital(20000);
    portfolioUpdate.setTimePeriod("Yearly");
    portfolioUpdate.setStartDate("2010-01");
    List<AssetCreation> assetList = new ArrayList<>();
    assetList.add(new AssetCreation("AAMC", 0.5));
    assetList.add(new AssetCreation("YPF", 0.3));
    assetList.add(new AssetCreation("AAIC", 0.2));
    portfolioUpdate.setAssetList(assetList);

    // Serialize the object to JSON
    String portfolioUpdateJson = new ObjectMapper().writeValueAsString(portfolioUpdate);
    System.out.println(portfolioUpdateJson);

    
    // Perform a PUT request to the updatePortfolio endpoint
    mockMvc.perform(put("/portfolio/updatePortfolio/{userId}/{portfolioId}", id2, portfolioid)
        .contentType(MediaType.APPLICATION_JSON)
        .content(portfolioUpdateJson)
        .header("Authorization", "Bearer " + token2))
        .andExpect(status().isOk())
        .andDo(MockMvcResultHandlers.print());

}
@Ignore
@Test
public void testDeletePortfolio() throws Exception {
    // input your test data here
    int id = 1;
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlcjFAZW1haWwuY29tIiwiaWF0IjoxNjk5MTczNzAzLCJleHAiOjE2OTkyNjAxMDN9.6_8NHFXzBYKDw9XUi1SmT7j4IuQpaD1TaFAdo5J8_00";
    int portfolioid = 1;


// Perform a DELETE request to the deletePortfolio endpoint
    mockMvc.perform(delete("/portfolio/deletePortfolio/{userId}/{portfolioId}", id, portfolioid)
    .header("Authorization", "Bearer " + token))
    .andExpect(status().isOk())
    .andExpect(content().string("Portfolio Deleted!"))
    .andDo(MockMvcResultHandlers.print());
}




}
