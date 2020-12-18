package tr.edu.yildiz.yazilimkalite.librarymanagement.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.mapping.StatisticResultMapping;
import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.response.Response;
import tr.edu.yildiz.yazilimkalite.librarymanagement.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserApiController {
    @Autowired
    private UserService userService;

    @GetMapping("/statistics/count")
    public ResponseEntity<Response<List<StatisticResultMapping>>> getUserCountStatistics(
            @RequestParam(required = false) String by) {
        return new ResponseEntity<>(new Response<>(true, userService.getUserCountByStatus(), "usercounts"),
                HttpStatus.OK);
    }

}