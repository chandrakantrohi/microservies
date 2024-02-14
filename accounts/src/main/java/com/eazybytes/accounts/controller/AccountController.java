package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.constant.AccountsConstants;
import com.eazybytes.accounts.dto.AccountContactInfoDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.dto.ResponseDto;
import com.eazybytes.accounts.service.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
	name = "CRUD rest api for Accounts in EAZY Bank",
		description = "CRUD rest api for Accounts in EAZY Bank to CREATE,UPDATE,DELETE AND GET ACCOUNTS DETAILS"
)
@RestController
@RequestMapping(path = "/api",produces = {MediaType.APPLICATION_JSON_VALUE})
//@AllArgsConstructor
@Validated
//@NoArgsConstructor
public class AccountController {

	@Autowired
	private Environment environment;

	@Autowired
	AccountContactInfoDto accountContactInfoDto;
/*
	@Value("${build.version}")
	private String buildVersion;*/
	public AccountController(IAccountsService iAccountsService){
		this.iAccountsService=iAccountsService;
	}

	private IAccountsService iAccountsService;

	@Operation(
			summary = "Create Account rest api",
			description = "Rest api to create Account and Customer inside eazy bank"
	)
	@ApiResponse(
			responseCode = "201",
			description ="HTTP status create"
	)
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){
		iAccountsService.createAccount(customerDto);
		return  ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(AccountsConstants.STATUS_201,AccountsConstants.MESSAGE_201));
	}

	@GetMapping("/fetch")
	public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam String mobileNumber){

		CustomerDto customerDto=iAccountsService.fetchAccount(mobileNumber);
		return ResponseEntity.status(HttpStatus.OK).body(customerDto);
	}

	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {
		boolean isUpdated = iAccountsService.updateAccount(customerDto);
		if(isUpdated) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
		}else{
			return ResponseEntity
					.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
		}
	}

	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam
															@Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
															String mobileNumber) {
		boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
		if(isDeleted) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
		}else{
			return ResponseEntity
					.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
		}
	}

	@GetMapping("/build-info")
	public ResponseEntity<String> getBuildVersion(){

		return ResponseEntity.
				status(HttpStatus.OK).
				body("buildVersion");
	}

	@GetMapping("/java-version")
	public ResponseEntity<String> getJavaVersion(){

		return ResponseEntity.status(HttpStatus.OK).
				body(environment.getProperty("JAVA_HOME"));
	}

	@GetMapping("/contact-info")
	public ResponseEntity<AccountContactInfoDto> getContactInfo(){

		return ResponseEntity.status(HttpStatus.OK).
				body(accountContactInfoDto);
	}
}
