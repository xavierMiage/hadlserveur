package databaseManager;

public class DatabaseManager extends AbstractDatabaseManager {
	
	private ArticleSql article;
	private UserSql user;
	
	public DatabaseManager() {
		
	}
	
	public DatabaseManager(String connectionString) throws Exception {
		this.connectionString = connectionString;
		this.readDataBase();
		this.article = new ArticleSql();
		this.article.connectionString = connectionString;
		this.article.readDataBase();
		this.user = new UserSql();
		this.user.connectionString = connectionString;
		this.user.readDataBase();
	}

	public ArticleSql getArticle() {
		return article;
	}

	public void setArticle(ArticleSql article) {
		this.article = article;
	}

	public UserSql getUser() {
		return user;
	}

	public void setUser(UserSql user) {
		this.user = user;
	}
}
