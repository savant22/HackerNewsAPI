package com.example.hack.serviceTest;

import com.example.hack.DTO.CommentResponse;
import com.example.hack.entities.Comments;
import com.example.hack.entities.Stories;
import com.example.hack.service.HackerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest(classes=HackerNewsServiceTest.class)
public class HackerNewsServiceTest {

    @InjectMocks
    HackerService service;

    @Mock
    RestTemplate restTemplate;

    private static final Date CURRENT_DATE = new Date();
    private static final Timestamp TIME_STAMP = new Timestamp(CURRENT_DATE.getTime());

    String baseApiItemsUrl="https://hacker-news.firebaseio.com/v0/item/";
    String topStoriesApiUrl="https://hacker-news.firebaseio.com/v0/topstories.json";

    @BeforeEach
    public void setUp()
    {
        dynamicMockValues();

    }
    @Test
    public void fetchTopStoriesFromApiTest(){


        List<Integer> storyIds= Arrays.asList(1,2,3);
        Stories story=new Stories();
        List kids= Arrays.asList(9,8,7);
        story.setBy("Martin");
        story.setId(1);
        story.setKids(kids);
        story.setTitle("The latest news");
        story.setScore(10l);
        story.setUrl("www.google.com");
        story.setTime(TIME_STAMP);
        List<Stories> expectedResponse=new ArrayList<>();
        expectedResponse.add(story);
        when(restTemplate.getForObject(anyString(), eq(List.class))).thenReturn(storyIds);
        when(restTemplate.getForObject(anyString(), eq(Stories.class))).thenReturn(story);
        List<Stories> topStories=this.service.fetchTopStoriesFromApi();
        assertNotNull(topStories);
        assertEquals(expectedResponse,topStories);
    }

    @Test
    public void fetchTopCommentsFromApiTest(){
        long id=10l;
        Stories story=new Stories();
        List kids= Arrays.asList(9,8);
        story.setBy("Apoorva");
        story.setId(1);
        story.setKids(kids);
        story.setTitle("New Updated news");
        story.setScore(10l);
        story.setUrl("www.github.com");
        story.setTime(TIME_STAMP);
        Comments comments = new Comments();
        List commentKids= Arrays.asList(100,111,122);
        comments.setBy("Joe");
        comments.setId(15l);
        comments.setText("The news just came");
        comments.setKids(commentKids);
        CommentResponse c = new CommentResponse(comments.getBy(), comments.getText(), comments.getKids().size());
        List<CommentResponse> commentResponses = new ArrayList<>(List.of(c, c));


        when(restTemplate.getForObject(anyString(), eq(Comments.class))).thenReturn(comments);
        when(restTemplate.getForObject(anyString(), eq(Stories.class))).thenReturn(story);
        List<CommentResponse> actualResponses=this.service.fetchTopCommentsFromApi(id);
        assertNotNull(actualResponses);
        assertEquals(commentResponses,actualResponses);
    }

    public void dynamicMockValues(){
        ReflectionTestUtils.setField(this.service,"restTemplate",restTemplate,RestTemplate.class);
        ReflectionTestUtils.setField(this.service,"baseApiItemsUrl",baseApiItemsUrl,String.class);
        ReflectionTestUtils.setField(this.service,"topStoriesApiUrl",topStoriesApiUrl,String.class);
    }
}
