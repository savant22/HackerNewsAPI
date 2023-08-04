package com.example.hack.service;

import com.example.hack.DTO.CommentResponse;
import com.example.hack.entities.Comments;
import com.example.hack.entities.Stories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HackerService{

    @Autowired
    RestTemplate restTemplate;

    @Value("${topStoriesApiUrl}")
    private String topStoriesApiUrl;

    @Value("${baseApiItemsUrl}")
    private String baseApiItemsUrl;

    /*
    This method will make a call to NewsHacker Api and
    will fetch all stories, and then it will sort the stories on the basis
    of time of last 15 minutes, and then it will sort stories by its
    score.
    * */
    @Cacheable("apiResponseCache")
    public List<Stories> fetchTopStoriesFromApi() {

        List<Integer> storyIds = restTemplate.getForObject(topStoriesApiUrl.trim(), List.class);

        Map<Timestamp, Stories> treemap=new TreeMap<Timestamp, Stories>(Collections.reverseOrder());
        storyIds.forEach(storyId->{
            String  storyUrl = baseApiItemsUrl + storyId + ".json";
            Stories story = restTemplate.getForObject(storyUrl.trim(), Stories.class);


            treemap.put(story.getTime(),story);

        });

        List<Stories> listTopTenStories = treemap.values().stream().limit(10).sorted(Comparator.comparing(Stories::getScore).reversed()).toList();

        return listTopTenStories;
    }


    /*
    This method will make a call to NewsHacker Api with a particular
    story id, and it will return all comments of that story, and then
    it will sort the comments on the basis of its count of child
    counts.
    **/

    public List<CommentResponse>  fetchTopCommentsFromApi(@PathVariable long id) {
        String apiUrl = baseApiItemsUrl+id+".json";
        Stories story = restTemplate.getForObject(apiUrl.trim(), Stories.class);

        List<Integer> commentList=story.getKids();


            List<Comments> addCommentsToList = new ArrayList<>();
        if(commentList!=null) {
            for (Integer c : commentList) {
                String apiUrlForComments = baseApiItemsUrl + c + ".json";

                Comments comment = restTemplate.getForObject(apiUrlForComments.trim(), Comments.class);
                addCommentsToList.add(comment);
            }
        }


            List<Comments> commentListNew = addCommentsToList.stream()
                    .sorted(Comparator
                            .comparing(s -> s.getKids() == null ? 0 : s.getKids().size(), Comparator.reverseOrder())).limit(10)
                    .collect(Collectors.toList());

            List<CommentResponse> commentResponses = new ArrayList<>();
            commentListNew.forEach(comment -> {
                if(comment.getKids()!=null) {
                    CommentResponse c = new CommentResponse(comment.getBy(), comment.getText(), comment.getKids().size());
                    commentResponses.add(c);
                }
            });


        return  commentResponses;
    }


}
