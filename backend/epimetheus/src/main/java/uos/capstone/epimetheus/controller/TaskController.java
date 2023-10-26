package uos.capstone.epimetheus.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import uos.capstone.epimetheus.dtos.TaskStep;
import uos.capstone.epimetheus.dtos.llamaTasks.SubTaskResolver;
import uos.capstone.epimetheus.service.TaskSerivce;


@RestController
@RequiredArgsConstructor
@Log4j2
public class TaskController {

    private final TaskSerivce taskSerivce;

    @PostMapping(path = "/tasks", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<SubTaskResolver> getTaskData(@RequestBody String taskName) {
        log.info(taskName);
        return taskSerivce.getSubTaskListInStream(taskName);
    }

    @PostMapping(path = "/save")
    public ResponseEntity<String> saveCode(@RequestBody TaskStep taskStep) {
        log.info("[/save] Save Code - " + taskStep);
        String response = taskSerivce.saveCode(taskStep);
        HttpStatusCode status;
        if(response.equals("not code")){
            status = HttpStatus.BAD_REQUEST;
        }else if(response.equals("success")){
            status = HttpStatus.OK;
        }else{
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(status).body(response);
    }

}

