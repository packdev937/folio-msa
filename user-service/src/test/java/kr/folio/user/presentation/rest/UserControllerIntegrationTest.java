package kr.folio.user.presentation.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import kr.folio.user.application.ports.input.UserApplicationUseCase;
import kr.folio.user.presentation.dto.request.CreateUserRequest;
import kr.folio.user.presentation.dto.response.user.CreateUserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@DisplayName("UserController 테스트 ")
@WebMvcTest(UserController.class)
class UserControllerIntegrationTest {

    // 가상의 HTTP 요청을 만들어 컨트롤러를 테스트
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    // @Mock (POJO) 이 아닌 @MockBean
    @MockBean
    private UserApplicationUseCase userApplicationUseCase;

//    @InjectMocks
//    private UserController userController;

    @BeforeEach
    void setUp() {
//        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userApplicationUseCase)).build();

        objectMapper = new ObjectMapper();
        // LocalDate, LocalDateTime 등을 직렬화/역직렬화 가능
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void 유저를_생성한다() throws Exception {
        // Given
        String id = "packdev937";
        String message = "가입이 완료 되었습니다.";
        CreateUserRequest createUserRequest = new CreateUserRequest(id, "admin",
            LocalDate.of(1999, 3, 27));
        CreateUserResponse createUserResponse = new CreateUserResponse(id,
            message);

        when(userApplicationUseCase.createUser(any(CreateUserRequest.class))).thenReturn(
            createUserResponse);

        // When & Then
        MvcResult mvcResult = mockMvc.perform(post("/users")
	.contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
	.content(objectMapper.writeValueAsString(createUserRequest)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.userId").value(id))
            .andExpect(jsonPath("$.message").value(message))
            .andReturn();

        // UTF-8 추가
        String response = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(objectMapper.writeValueAsString(createUserResponse), response);
        // 예상한 횟수 만큼 호출, 메소드 호출 여부, 호출된 인자 등을 확인
        verify(userApplicationUseCase, times(1)).createUser(any(CreateUserRequest.class));
    }

}