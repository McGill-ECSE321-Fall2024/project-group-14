package ca.mcgill.ecse321_group14.GameShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ca.mcgill.ecse321_group14.GameShop.dto.PolicyRequestDto;
import ca.mcgill.ecse321_group14.GameShop.dto.PolicyResponseDto;
import ca.mcgill.ecse321_group14.GameShop.model.Policy;
import ca.mcgill.ecse321_group14.GameShop.service.PolicyService;



@RestController
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    /**
     * Create a policy
     *
     * @param policyRequestDto
     * @return PolicyResponseDto
     */
    @PostMapping("/policy")
    public ResponseEntity<PolicyResponseDto> createPolicy(@RequestBody PolicyRequestDto policyRequestDto) {
        try {
            // Attempt to create the policy
            Policy policy = policyService.createPolicy(policyRequestDto.getDescription());

            // Return the created policy with a 201 Created status
            return new ResponseEntity<>(new PolicyResponseDto(policy), HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            // Print the exception message (for logging/debugging purposes)
            System.out.println(e.getMessage());

            // Return an empty response body with a 400 Bad Request status
            return new ResponseEntity<>(new PolicyResponseDto(), HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Get a policy by id
     *
     * @param id
     * @return PolicyResponseDto
     */
    @GetMapping("/policy/{id}")
    public ResponseEntity<PolicyResponseDto> getPolicy(@PathVariable int id) {
        try {
            // Attempt to retrieve the policy
            Policy policy = policyService.getPolicy(id);

            // Return the found policy with a 200 OK status
            return new ResponseEntity<>(new PolicyResponseDto(policy), HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            // Log the exception message (optional)
            System.out.println(e.getMessage());

            // Return an empty response body with a 404 Not Found status
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    /**
     * Update a policy by id
     *
     * @param id
     * @param policyRequestDto
     * @return PolicyResponseDto
     */
    @PostMapping("/policy/update/{id}")
    public ResponseEntity<PolicyResponseDto> updatePolicy(@PathVariable int id, @RequestBody PolicyRequestDto policyRequestDto) {
        try {
            // Attempt to update the policy
            Policy policy = policyService.updatePolicy(id, policyRequestDto.getDescription());

            // Return the updated policy with a 200 OK status
            return new ResponseEntity<>(new PolicyResponseDto(policy), HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            // Log the exception message (optional)
            System.out.println(e.getMessage());

            // Check if the exception is due to non-existing policy
            if (e.getMessage().equals("Policy does not exist!")) {
                // Return 404 Not Found if the policy doesn't exist
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            } else {
                // Return 400 Bad Request for other input-related issues
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }
    }


    /**
     * Delete a policy by id
     *
     * @param id
     */
    @DeleteMapping("/policy/{id}")
    public ResponseEntity<Void> deletePolicy(@PathVariable int id) {
        try {
            // Attempt to delete the policy
            policyService.deletePolicy(id);

            // Return 204 No Content if deletion is successful
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (IllegalArgumentException e) {
            // Log the exception message (optional)
            System.out.println(e.getMessage());

            // Return 404 Not Found if the policy does not exist
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
