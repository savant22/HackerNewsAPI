package com.example.hack.controller;

import com.example.hack.DTO.CommentResponse;
import com.example.hack.entities.Stories;
import com.example.hack.service.HackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
public class HackerNewsController {

    @Autowired
    HackerService hackerService;


    /*
    This controller method fetchTopTenStoriesFromApi()
    will call service method to fetch top 10 stories
    * */
    @GetMapping("/top-stories")
    public List<Stories> fetchTopTenStoriesFromApi() {

        return hackerService.fetchTopStoriesFromApi();
    }

    /*
    This controller method showAllCommentsOfStory()
     will take story id as an argument and then call
      service method to fetch all comments of particular story
      */

    @GetMapping("/comments/{id}")
    public  List<CommentResponse> showAllCommentsOfStory(@PathVariable long id)
    {
        return hackerService.fetchTopCommentsFromApi(id);
    }

    /*
    This method getAllPastStories will call service method
    fetchTopStoriesFromApi which will result the cached
    stories
    * */

    @GetMapping("/past-stories")
        public List<Stories> getAllPastStories()
        {
            return hackerService.fetchTopStoriesFromApi();
        }
}
