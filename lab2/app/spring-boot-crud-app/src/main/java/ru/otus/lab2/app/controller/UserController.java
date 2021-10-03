package ru.otus.lab2.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//import io.prometheus.client.CollectorRegistry;
//import io.prometheus.client.Counter;
//import io.prometheus.client.Summary;
import ru.otus.lab2.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.otus.lab2.app.model.User;

@CrossOrigin(origins = "http://localhost:8888")
@RestController
@RequestMapping("/api")
public class UserController {

	//static final Counter requestsTotal = Counter.build().name("crud_app_requests").help("Total number of requests").register();
	//static final Summary requestLatency = Summary.build().name("crud_app_request_latency_ms").help("Request latency in ms.").register();

	//private final CollectorRegistry registry;

	@Autowired
	UserRepository userRepository;

	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers() {
		//Summary.Timer requestTimer = requestLatency.startTimer();
		try {
			List<User> users = new ArrayList<User>();
			userRepository.findAll().forEach(users::add);

			if (users.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		finally {
			//requestsTotal.inc();
			//requestTimer.observeDuration();
		}
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
		//Summary.Timer requestTimer = requestLatency.startTimer();
		try {
			Optional<User> userData = userRepository.findById(id);

			if (userData.isPresent()) {
				return new ResponseEntity<>(userData.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		finally {
			//requestsTotal.inc();
			//requestTimer.observeDuration();
		}
	}

	@PostMapping("/users")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		//Summary.Timer requestTimer = requestLatency.startTimer();
		try {
			User _user = userRepository
					.save(new User(user.getName()));
			return new ResponseEntity<>(_user, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		finally {
			//requestsTotal.inc();
			//requestTimer.observeDuration();
		}
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
		//Summary.Timer requestTimer = requestLatency.startTimer();
		try {
			Optional<User> userData = userRepository.findById(id);

			if (userData.isPresent()) {
				User _user = userData.get();
				_user.setName(user.getName());
				return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		finally {
			//requestsTotal.inc();
			//requestTimer.observeDuration();
		}
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
		//Summary.Timer requestTimer = requestLatency.startTimer();
		try {
			userRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		finally {
			//requestsTotal.inc();
			//requestTimer.observeDuration();
		}
	}

	//@GetMapping("/metrics")
	//public ResponseEntity<String> getMetrics() {
	//	try {
	//		return new ResponseEntity<>( registry.filteredMetricFamilySamples(), HttpStatus.OK);
	//	} catch (Exception e) {
	//		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	//	}
	//}

}
