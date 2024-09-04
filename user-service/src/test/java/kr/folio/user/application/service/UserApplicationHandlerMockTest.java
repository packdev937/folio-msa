package kr.folio.user.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import feign.FeignException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import kr.folio.user.application.handler.UserApplicationHandler;
import kr.folio.user.application.mapper.UserDataMapper;
import kr.folio.user.application.ports.output.UserRepository;
import kr.folio.user.domain.core.entity.User;
import kr.folio.user.domain.service.UserDomainUseCase;
import kr.folio.user.infrastructure.client.FeedServiceClient;
import kr.folio.user.presentation.dto.response.feed.FeedsResponse;
import kr.folio.user.presentation.dto.response.feed.RetrieveFeedResponse;
import kr.folio.user.presentation.dto.response.user.UserHomeResponse;
import kr.folio.user.presentation.dto.response.user.UserProfileResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserApplicationHandlerMockTest {

    @Mock
    private UserDomainUseCase userDomainService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDataMapper userDataMapper;

    @Mock
    private FeedServiceClient feedServiceClient;

    // Mock 된 의존성들을 주입합니다.
    @InjectMocks
    private UserApplicationHandler userApplicationHandler;

    @BeforeEach
    void setUp() {
        // Mock 객체를 초기화 합니다.
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 홈_화면을_조회한다() {
        // Given
        User mockUser = User.builder()
            .id("packdev937")
            .nickname("admin")
            .profileImageUrl("profileUrl")
            .build();

        UserProfileResponse mockUserProfile = new UserProfileResponse(
            mockUser.getId(),
            mockUser.getNickname(),
            mockUser.getProfileImageUrl()
        );

        FeedsResponse mockFeedsResponse = new FeedsResponse(
            List.of(
	new RetrieveFeedResponse(1L, "photoImageUrl1", LocalDateTime.of(2021, 1, 1, 0, 0)),
	new RetrieveFeedResponse(2L, "photoImageUrl2", LocalDateTime.of(2021, 1, 1, 0, 0)),
	new RetrieveFeedResponse(3L, "photoImageUrl3", LocalDateTime.of(2021, 1, 1, 0, 0))
            )
        );

        // Mocking the methods
        when(userRepository.findUserById(mockUser.getId())).thenReturn(Optional.of(mockUser));
        // Private 메소드라도 동작을 정의해주어야 합니다.
        when(userApplicationHandler.retrieveUserProfile(mockUser.getId())).thenReturn(
            mockUserProfile);
        when(feedServiceClient.retrieveFeeds(mockUser.getId())).thenReturn(mockFeedsResponse);
        when(userDataMapper.toRetrieveUserHomeResponse(mockUserProfile, mockFeedsResponse))
            .thenReturn(new UserHomeResponse(mockUserProfile, mockFeedsResponse));

        // When
        UserHomeResponse response = userApplicationHandler.retrieveUserHome(mockUser.getId());

        // Then
        assertNotNull(response);
        assertEquals(mockUserProfile, response.userProfile());
        assertEquals(mockFeedsResponse, response.feeds());
    }

    @Test
    void 예외를_발생한다() {
        // Given
        User mockUser = User.builder()
            .id("packdev937")
            .nickname("admin")
            .profileImageUrl("profileUrl")
            .build();

        UserProfileResponse mockUserProfile = new UserProfileResponse(
            mockUser.getId(),
            mockUser.getNickname(),
            mockUser.getProfileImageUrl()
        );

        // Mocking the methods
        when(userRepository.findUserById(mockUser.getId())).thenReturn(Optional.of(mockUser));
        when(userApplicationHandler.retrieveUserProfile(mockUser.getId())).thenReturn(
            mockUserProfile);
        when(feedServiceClient.retrieveFeeds(mockUser.getId())).thenThrow(FeignException.class);
        when(userDataMapper.toRetrieveUserHomeResponse(mockUserProfile, null))
            .thenReturn(new UserHomeResponse(mockUserProfile, null));

        // When
        UserHomeResponse response = userApplicationHandler.retrieveUserHome(mockUser.getId());

        // Then
        assertNotNull(response);
        assertEquals(mockUserProfile, response.userProfile());
        assertNull(response.feeds());
    }

}
