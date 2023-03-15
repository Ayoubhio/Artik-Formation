package artik.sprinapplication.Code;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Api(value = "Square API",protocols = "http")
public class NbrController
    {
        @Autowired
        private final NbrService nbrService;

        public NbrController(NbrService squareService) {
            this.nbrService = squareService;
        }

//        pour l'execution  ,vous passer un nombre a l'url et apres son carre s'affiche : http://localhost:8080/square/7
        @ApiOperation(value = "Calculate square of a number")
        @ApiResponses(value = {
                @ApiResponse(code = 200, message = "Successfully calculated square"),
                @ApiResponse(code = 400, message = "Invalid input supplied"),
                @ApiResponse(code = 500, message = "Internal server error")
        })
        @GetMapping("/square/{number}")
        public int getSquare(@PathVariable int number)
        {
            return nbrService.calculateSquare(number);
        }
    }
