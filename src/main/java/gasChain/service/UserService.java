package gasChain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import gasChain.entity.User;
import gasChain.repository.UserRepository;

@Service
public abstract class UserService {

	private UserRepository repo;
	
	public UserService(UserRepository repo) { this.repo = repo; }
	
	public UserService add(User user) { repo.save(user); return this; }
	
	public boolean isEmpty() { return repo.count() <= 0; }
	
	public List<User> findAll() { return repo.findAll(); }
	
	public User findByUsername(String username) { return repo.findByUsername(username); }
	
	public boolean existsUser(String username) { return repo.findByUsername(username) != null; }

}
