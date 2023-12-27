package com.hcl.ask_buddy.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.hcl.ask_buddy.entity.UnConfirmedUser;
import com.hcl.ask_buddy.entity.UserOtp;
import com.hcl.ask_buddy.entity.User_entity;
import com.hcl.ask_buddy.repository.UnconfirmUserRepo;
import com.hcl.ask_buddy.repository.UserOtp_repository;
import com.hcl.ask_buddy.repository.User_repository;
import com.hcl.ask_buddy.security.AuthenticatedUser;
import com.hcl.ask_buddy.security.JwtUserDetailsService;
import com.hcl.ask_buddy.security.JwtUtil;

@Service
public class UserService {

	@Autowired
	User_repository user;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtTokenUtil;

//	@Autowired
	private final JavaMailSender javaMailSender;

	@Autowired
	private UserOtp_repository userOtp;
	
	@Autowired
	private AuthenticatedUser authUser;
	
	@Autowired
	private UnconfirmUserRepo unconfirmRepo;

	@Value("${spring.mail.username")
	private String sender;
	
	@Autowired
    public UserService(JavaMailSender mailSender) {
        this.javaMailSender = mailSender;
    }
	
	private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
	private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

	public static String generateNewToken() {
	    byte[] randomBytes = new byte[24];
	    secureRandom.nextBytes(randomBytes);
	    return base64Encoder.encodeToString(randomBytes);
	}

	public List<User_entity> user_List() {
		return user.findAll();
	}
	
	public String gethtml(String htmlpath)
	{
		String htmlString = "";
		try {
			Path path = Paths
					.get(htmlpath);
			byte[] bytes = Files.readAllBytes(path);
			htmlString = new String(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return htmlString;
	}

	
	public String register(User_entity users) throws Exception {
//	    EmailValidator emailValidator = new EmailValidator();
//	    boolean validateEmail = emailValidator.isValidEmail(users.getMail());
//	    System.out.println(validateEmail);
		UnConfirmedUser unconfirmUser = new UnConfirmedUser();
		if (user.existsById(users.getMail()) != true) {
			System.out.println(users.getPassword());
			unconfirmUser.setUsername(users.getUsername());
			unconfirmUser.setMail(users.getMail());
			unconfirmUser.setSap_Id(users.getSap_Id());
			unconfirmUser.setPassword(users.getPassword());
			System.out.println(users.getPassword());
			unconfirmUser.setToken(generateNewToken());
			try {
			MimeMessage message = javaMailSender.createMimeMessage();
			message.setFrom(new InternetAddress(sender));
			message.setRecipients(MimeMessage.RecipientType.TO, users.getMail());
			message.setSubject("Askbuddy Application link verification");
			String htmlTemplate = gethtml("C:\\Users\\S S Rao\\OneDrive\\Documents\\AB-app\\caskbuddy\\Askbuddynew\\Ask-Buddy_MS_User\\src\\main\\resources\\html\\Confirmation.html");
			htmlTemplate = htmlTemplate.replace("{$username}", users.getUsername());
			htmlTemplate = htmlTemplate.replace("{$confirmlink}", "http://localhost:8086/api/users/confirmUser?token=" + unconfirmUser.getToken());
			message.setContent(htmlTemplate, "text/html; charset=utf-8");

			javaMailSender.send(message);
			}
			catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
				return "Invalid Mail Id";
			}
			System.out.println(unconfirmRepo.existsById(users.getMail()));
			if(unconfirmRepo.existsById(users.getMail()) == false)
			{
				System.out.println("coming here");
                unconfirmRepo.save(unconfirmUser);
			}
			else
				unconfirmRepo.updateToken(unconfirmUser.getToken(), users.getSap_Id());
			System.out.println("sapId:" + users.getSap_Id() + "userName" + users.getUsername() + "passWord"
					+ users.getPassword() + "dateOfJoining");
			return "Success";
		}
		else
		{
			return "Existing user";
		}

}

	public String ConfirmUser(String token)
	{
		UnConfirmedUser unconfirmuser = unconfirmRepo.getUserData(token);
//		System.out.println(unconfirmuser.getMail());
		if(unconfirmuser != null && !user.existsById(unconfirmuser.getMail()))
		{
			User_entity users = new User_entity();
			users.setUsername(unconfirmuser.getUsername());
			users.setMail(unconfirmuser.getMail());
			users.setPassword(passwordEncoder.encode(unconfirmuser.getPassword()));
			users.setSap_Id(unconfirmuser.getSap_Id());
			user.save(users);
			return "Successfully Verified Please login";
		}
		else
			return "Please check the link";
	}

	public String login(String mail, String password) throws Exception {
		if (user.existsById(mail)) {
			User_entity userData = user.getById(mail);
			authenticate(userData.getMail(), password);
			final UserDetails userDetails = userDetailsService.loadUserByUsername(userData.getMail());
			final String token = jwtTokenUtil.generateToken(userDetails);
			System.out.println(token);
			return token;
		}
		else if (unconfirmRepo.existsById(mail)) {
			return "We have send the confirmation mail please click on the link then login";

		} else
			return "Invalid User Id or Password";
	}

	// User Authentication
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

	public boolean delete(@PathVariable String mail) {
		try {
			user.deleteById(mail);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public String generateOtp(String mail) throws MailSendException, AddressException, MessagingException
	{
		if(!user.existsById(mail))
			return "Invalid SAP_ID";
		User_entity userData = user.findById(mail).get();
		String otp = "";
		int randomNumber;
		for(int i = 0; i < 6; i++)
		{
			randomNumber = new Random().nextInt(9);
			otp = otp.concat(Integer.toString(randomNumber));
		}
		MimeMessage message = javaMailSender.createMimeMessage();
		message.setFrom(new InternetAddress(sender));
		message.setRecipients(MimeMessage.RecipientType.TO, userData.getMail());
		message.setSubject("Askbuddy Application forget password");
		String htmlTemplate = getHtmlCode(userData, otp);
		message.setContent(htmlTemplate, "text/html; charset=utf-8");
		javaMailSender.send(message);
		if(userOtp.getUser(userData) == null)
		{
			userOtp.save(UserOtp.builder().dtntm(LocalDateTime.now()).otp(otp).user(userData).build());
		}
		else
			userOtp.updateOtp(otp, LocalDateTime.now(), userData);
		
		return otp;
		
	}

	public String getHtmlCode(User_entity userData, String otp) {
		String htmlString = "";
		try {
			Path path = Paths
					.get("C:\\Users\\S S Rao\\OneDrive\\Documents\\AB-app\\caskbuddy\\Askbuddynew\\Ask-Buddy_MS_User\\src\\main\\resources\\html\\index.html");
			byte[] bytes = Files.readAllBytes(path);
			htmlString = new String(bytes);
			htmlString = htmlString.replace("mailId", userData.getMail());
			htmlString = htmlString.replace("otp", otp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return htmlString;
	}

//	@Scheduled(fixedRate = 2000)
//	public void destroyOtp() {
//		List<UserOtp> userOtpList = userOtp.findAll();
//		for (UserOtp userotp : userOtpList) {
//			Duration duration = Duration.between(userotp.getDtntm(), LocalDateTime.now());
//			System.out.println(duration.getSeconds());
//			if (duration.getSeconds() > 5 * 60) {
//				System.out.println(userotp.getSno());
////				userOtp.save(userotp);
////				userOtp.deleteById(userotp.getSno());
//				userOtp.deleteByUser(userotp.getUser());
////				System.out.println(userOtp.updateOtp("2358", userotp.getDtntm(), userotp.getUser()));
//			}
//		}
//	}

	public String updatePassword(String mail, long sap_id, String password) {
		System.out.println(mail + sap_id + password);
		if (user.updatePassword(passwordEncoder.encode(password), sap_id, mail) != 0) {
			return "Password Updated Successfully";
		} else
			return "Failed to update password";
	}

	public User_entity verifyOtp(String mail, String otp) {
		if (user.existsById(mail)) {
			User_entity userData = user.findById(mail).get();
			if (otp.equals(userOtp.getUser(userData).getOtp())) {			
				return userOtp.getUser(userData).getUser();
			}
			return null;
		} else
			return null;
	}
	
	public User_entity getUserData(String token)
	{
		String username = jwtTokenUtil.getUsernameFromToken(token);
		User_entity userData = user.getByMail(username);
		if(userData.getPic() != null)
		userData.setPic(decompressBytes(userData.getPic()));
		return userData;
	}
	
	public int uploadImage(MultipartFile file) throws IOException
	{
		byte[] img = compressBytes(file.getBytes());
		System.out.println(file.getOriginalFilename());
//		User_entity userdata = User_entity.builder().pic(file.getBytes()).build();
		System.out.println(img);
		return user.uploadImage(img, authUser.getUser().getSap_Id());
	}
	
	public byte[] getImage(String mail)
	{
		if( user.findById(mail).isPresent())
		{
		User_entity userdata = user.findById(mail).get();
		return decompressBytes(userdata.getPic());  
		}
		return null;
	}
	
	// compress the image bytes before storing it in the database
		public static byte[] compressBytes(byte[] data) {
			Deflater deflater = new Deflater();
			deflater.setInput(data);
			deflater.finish();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
			byte[] buffer = new byte[4 * 1024];
			while (!deflater.finished()) {
				int count = deflater.deflate(buffer);
				outputStream.write(buffer, 0, count);
			} 
			try {
				outputStream.close();
			} catch (IOException e) {
			}
			System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
			return outputStream.toByteArray();
		}
		// uncompress the image bytes before returning it to the angular application
		public static byte[] decompressBytes(byte[] data) {
			Inflater inflater = new Inflater();
			inflater.setInput(data);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
			byte[] buffer = new byte[4 * 1024];
			try {
				while (!inflater.finished()) {
					int count = inflater.inflate(buffer);
					outputStream.write(buffer, 0, count);
				}
				outputStream.close();
			} catch (IOException ioe) {
			} catch (DataFormatException e) {
			}
			return outputStream.toByteArray();
		}
}
