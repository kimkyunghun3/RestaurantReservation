package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class) //스프링 이용하여 테스트 실행하도록 설정
@WebMvcTest(RestaurantController.class)   //restuarnat 컨트롤럴 실행한다고 명시해줄 수 있따.
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean    //가짜 처리를 통해 처리할 수 있다(Controller 대신)
    private RestaurantService restaurantService;

//    @SpyBean(RestaurantService.class)
//    private RestaurantService restaurantService;

//    @SpyBean(RestaurantRepositoryImpl.class)  //사용한 레퍼지토리 사용하겠다고 주입해주는 것이다. 어떤것을 구현할 것인지 적어줘야한다.
//    private RestaurantRepository restaurantRepository;
//
//    @SpyBean(MenuItemRepositoryImpl.class)
//    private MenuItemRepository menuItemRepository;

    @Test
    public void list() throws Exception {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant(1004L, "JOKER House", "Seoul"));
        given(restaurantService.getRestaurants()).willReturn(restaurants);

        mvc.perform (get("/restaurants"))
                .andExpect(status().isOk())

                .andExpect(content().string(
                        containsString("\"id\":1004")
                ))
                .andExpect((ResultMatcher) content().string(
                        containsString("\"name\":\"JOKER House\"")
            ));
    }

    @Test
    public void detail() throws Exception {
        Restaurant restaurant1 = new Restaurant(1004L, "JOKER House","Seoul");
        restaurant1.addMenuItem(new MenuItem("Kimchi"));

        Restaurant restaurant2 = new Restaurant(2020L,"Cyber Food","Seoul");

        given(restaurantService.getRestaurant(1004L)).willReturn(restaurant1);
        given(restaurantService.getRestaurant(2020L)).willReturn(restaurant2);

        mvc.perform(get("/restaurants/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1004")
                ))
                .andExpect((ResultMatcher) content().string(
                        containsString("\"name\":\"JOKER House\"")
                ))
        .andExpect(content().string(
                containsString("Kimchi")
        ));

        mvc.perform(get("/restaurants/2020"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":2020")
                ))
                .andExpect((ResultMatcher) content().string(
                        containsString("\"name\":\"Cyber Food\"")
                ));
    }


    @Test
    public void create() throws Exception {
//        Restaurant restaurant = new Restaurant(1234L,"BeRyong","Seoul");


        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Beryong\",\"address\":\"Busan\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location","/restaurants/null"))
                .andExpect(content().string("{}"));

        verify(restaurantService).addRestaurant(any());  //any는 어떤 객체를 넣어도 실행하게 된다(실행되는지 확인할 떄 사용)
    }
}