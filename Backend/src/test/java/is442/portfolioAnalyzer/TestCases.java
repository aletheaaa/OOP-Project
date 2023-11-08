package is442.portfolioAnalyzer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

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
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@SpringJUnitConfig
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class TestCases {
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
        registerRequest.setEmail("testuser3@email.com");
        registerRequest.setFirstname("test");
        registerRequest.setLastname("user3");
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
//             int id2 = 52 ;
//             String token2 = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlcjNAZW1haWwuY29tIiwiaWF0IjoxNjk5NDI3Mjc5LCJleHAiOjE2OTk1MTM2Nzl9.ZCE3_hfDh2ucP9h35IOleuzVpJoQfR9xCk1ZHP62CuQ";
//             int portfolioid = 2; //manual

//                // Perform a GET request to the getPortfolioDetails endpoint
//                MvcResult result = mockMvc.perform(get("/portfolio/getPortfolioDetails/{userId}/{portfolioId}", id2, portfolioid)
//                 .header("Authorization", "Bearer " + token2))
//                 .andExpect(status().isOk())
//                 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                 .andDo(MockMvcResultHandlers.print())
//                 .andReturn();

//             String responseContent = result.getResponse().getContentAsString();
//             System.out.println(responseContent);

//             // Check if the response contains the expected fields
//             JsonPath jsonPath = JsonPath.compile("$");
//             Object data = jsonPath.read(responseContent);
//             assertNotNull(data);

//             jsonPath = JsonPath.compile("$.portfolioName");
//             String portfolioName = jsonPath.read(responseContent);
//             assertEquals("testing portfolio", portfolioName);

//             jsonPath = JsonPath.compile("$.description");
//             String description = jsonPath.read(responseContent);
//             assertEquals("A test portfolio", description);

//             jsonPath = JsonPath.compile("$.capital");
//             int capital = jsonPath.read(responseContent);
//             assertEquals(10000, capital);

//             jsonPath = JsonPath.compile("$.timePeriod");
//             String timePeriod = jsonPath.read(responseContent);
//             assertEquals("Monthly", timePeriod);

//             jsonPath = JsonPath.compile("$.startDate");
//             String startDate = jsonPath.read(responseContent);
//             assertEquals("2000-01", startDate);

//             jsonPath = JsonPath.compile("$.assetList[0].symbol");
//             String symbol1 = jsonPath.read(responseContent);
//             assertEquals("AAPL", symbol1);

//             jsonPath = JsonPath.compile("$.assetList[0].weight");
//             double weight1 = jsonPath.read(responseContent);
//             assertEquals(0.5, weight1, 0.001);

//             jsonPath = JsonPath.compile("$.assetList[1].symbol");
//             String symbol2 = jsonPath.read(responseContent);
//             assertEquals("PFE", symbol2);

//             jsonPath = JsonPath.compile("$.assetList[1].weight");
//             double weight2 = jsonPath.read(responseContent);
//             assertEquals(0.3, weight2, 0.001);

//             jsonPath = JsonPath.compile("$.assetList[2].symbol");
//             String symbol3 = jsonPath.read(responseContent);
//             assertEquals("AAIC", symbol3);

//             jsonPath = JsonPath.compile("$.assetList[2].weight");
//             double weight3 = jsonPath.read(responseContent);
//             assertEquals(0.2, weight3, 0.001);
//             assertEquals(0.3, weight2, 0.001);

            
//         }
       

    

    

@Ignore
@Test
public void testUpdatePortfolio() throws Exception {
    // input your test data here
    int id2 = 2;
    String token2 = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlcjJAZW1haWwuY29tIiwiaWF0IjoxNjk5NDI3MDgzLCJleHAiOjE2OTk1MTM0ODN9.EOrPSOaPCRlJyAistLKjbDSgS7u9g6jQu05NHzsxo9I";
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
    int id = 2;
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlcjJAZW1haWwuY29tIiwiaWF0IjoxNjk5NDI3MDgzLCJleHAiOjE2OTk1MTM0ODN9.EOrPSOaPCRlJyAistLKjbDSgS7u9g6jQu05NHzsxo9I";
    int portfolioid = 1;


// Perform a DELETE request to the deletePortfolio endpoint
    mockMvc.perform(delete("/portfolio/deletePortfolio/{userId}/{portfolioId}", id, portfolioid)
    .header("Authorization", "Bearer " + token))
    .andExpect(status().isOk())
    .andExpect(content().string("Portfolio Deleted!"))
    .andDo(MockMvcResultHandlers.print());
}




}
