package com.example.bigbrotherbe.domain.member.controller;

import com.example.bigbrotherbe.domain.member.dto.request.ChangePasswordRequest;
import com.example.bigbrotherbe.domain.member.dto.request.MemberRequest;
import com.example.bigbrotherbe.domain.member.dto.request.SignUpDto;
import com.example.bigbrotherbe.domain.member.dto.response.AffiliationCollegeResponse;
import com.example.bigbrotherbe.domain.member.dto.response.MemberInfoResponse;
import com.example.bigbrotherbe.domain.member.dto.response.MemberResponse;
import com.example.bigbrotherbe.domain.member.entity.enums.AffiliationCode;
import com.example.bigbrotherbe.domain.member.entity.role.AffiliationListDto;
import com.example.bigbrotherbe.global.email.EmailRequest;
import com.example.bigbrotherbe.global.email.EmailVerificationResult;
import com.example.bigbrotherbe.global.exception.response.ApiResponse;
import com.example.bigbrotherbe.global.jwt.AuthUtil;
import com.example.bigbrotherbe.global.jwt.JwtToken;
import com.example.bigbrotherbe.global.jwt.JwtTokenProvider;
import com.example.bigbrotherbe.domain.member.service.MemberService;

import com.example.bigbrotherbe.global.jwt.entity.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.bigbrotherbe.global.exception.enums.SuccessCode.SUCCESS;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberControllerImpl implements MemberController {

    private final MemberService memberService;
    private final AuthUtil authUtil;
    private final JwtTokenProvider jwtTokenProvider;

    public ResponseEntity<ApiResponse<MemberResponse>> signUp(SignUpDto signUpDto) {
        MemberResponse memberResponse = memberService.userSignUp(signUpDto);

        return ResponseEntity.ok(ApiResponse.success(SUCCESS, memberResponse));
    }

    public ResponseEntity<ApiResponse<JwtToken>> signIn(MemberRequest memberRequest) {
        String memberEmail = memberRequest.getMemberEmail();
        String password = memberRequest.getMemberPass();
        JwtToken jwtToken = memberService.userSignIN(memberEmail, password);
        return ResponseEntity.ok(ApiResponse.success(SUCCESS, jwtToken));
    }


    public ResponseEntity<ApiResponse<AffiliationListDto>> test() {
        return ResponseEntity.ok(ApiResponse.success(SUCCESS, memberService.getMemberAffiliationRoleList()));

    }

    public ResponseEntity<ApiResponse<MemberInfoResponse>> inquireMemberInfo() {
        return ResponseEntity.ok(ApiResponse.success(SUCCESS, memberService.inquireMemberInfo()));
    }

    public ResponseEntity<ApiResponse<EmailVerificationResult>> verificateEmail(String email) {
        return ResponseEntity.ok(ApiResponse.success(SUCCESS, memberService.verificateEmail(email)));
    }

    public ResponseEntity<ApiResponse<EmailVerificationResult>> sendMessage(EmailRequest emailRequest) {
        memberService.sendCodeToEmail(emailRequest.getEmail());
        return ResponseEntity.ok(ApiResponse.success(SUCCESS, EmailVerificationResult.builder().authResult(true).build()));
    }


    public ResponseEntity<ApiResponse<EmailVerificationResult>> verificationEmail(String email, String code) {
        return ResponseEntity.ok(ApiResponse.success(SUCCESS, memberService.verifiedCode(email, code)));
    }


    public ResponseEntity<ApiResponse<Void>> changePassword(ChangePasswordRequest changePasswordRequest) {
        memberService.changePasswrd(changePasswordRequest.password());
        return ResponseEntity.ok(ApiResponse.success(SUCCESS));
    }

    public ResponseEntity<ApiResponse<List<AffiliationCollegeResponse>>> getCollegesList() {
        List<AffiliationCollegeResponse> collegesList = memberService.getColleges();
        return ResponseEntity.ok(ApiResponse.success(SUCCESS, collegesList));
    }

    public ResponseEntity<ApiResponse<TokenDto>> refreshToken(String refreshToken){
        return ResponseEntity.ok(ApiResponse.success(SUCCESS, memberService.refreshToken(refreshToken)));
    }
}
