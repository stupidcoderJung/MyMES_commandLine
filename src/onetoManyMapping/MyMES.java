package onetoManyMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class MyMES {
	static SessionFactory factory;
	static Session session;

	public static void main(String[] args) {
		factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

		try {
			while (true) {
				clearCMD();
				printMenu();
				int selectMenuNum = selectMenu();
				switch (selectMenuNum) {
				case 1:

					managementMaterial();
					break;
				case 2:

					managementProduct();
					break;
				case 3:

					managementBom();

					break;
				default:
					break;
				}
				session.close();
			}
		} finally {
			// TODO: handle finally clause

			factory.close();
		}
	}

	private static void managementBom() {
		// TODO bom 관리
		// 루프 시작
				boolean flag = true;
				while (flag) {
					session = factory.getCurrentSession();
					// 메뉴 출력
					printBOMMenu();
					// 메뉴 선택 정보 받아오기
					int selectMenuNum = selectMenu();
					// 자재관리를 자재정보 새로 입력,
					session.beginTransaction();
					switch (selectMenuNum) {
					// BOM정보 확인
					case 1:
						printBOMs();
						break;
					// BOM정보 입력
					case 2:
						insertNewBOM();
						break;

					// TODO: BOM정보 수정
					case 3:

						break;

					// TODO: BOM정보 삭제
					case 4:

						break;
					// 나가기
					case 5:
						flag = false;
						break;

					default:
						break;
					}
					session.getTransaction().commit();
					session.close();

				}
	}

	private static void managementProduct() {

		// TODO 물품관리 0503작성
		// 루프 시작
		boolean flag = true;
		while (flag) {
			session = factory.getCurrentSession();
			// 메뉴 출력
			printMaterailMenu();
			// 메뉴 선택 정보 받아오기
			int selectMenuNum = selectMenu();
			// 자재관리를 자재정보 새로 입력,
			session.beginTransaction();
			switch (selectMenuNum) {
			// 물품정보 확인
			case 1:
				printProducts();
				break;
			// 물품정보 입력
			case 2:
				insertNewProduct();
				break;

			// TODO: 물품정보 수정
			case 3:

				break;

			// TODO: 물품정보 삭제
			case 4:

				break;
			// 나가기
			case 5:
				flag = false;
				break;

			default:
				break;
			}
			session.getTransaction().commit();
			session.close();

		}

	}


	private static void managementMaterial() {
		// TODO 자재관리 0503작성
		// 루프 시작
		boolean flag = true;
		while (flag) {
			session = factory.getCurrentSession();
			// 메뉴 출력
			printMaterailMenu();
			// 메뉴 선택 정보 받아오기
			int selectMenuNum = selectMenu();
			// 자재관리를 자재정보 새로 입력,
			session.beginTransaction();
			switch (selectMenuNum) {
			// 자재정보 확인
			case 1:
				printMaterials();
				break;
			// 자재정보 입력
			case 2:
				insertNewMaterials();
				
				break;

			// TODO: 자재정보 수정
			case 3:

				break;

			// TODO: 자재정보 삭제
			case 4:

				break;
			// 나가기
			case 5:
				flag = false;
				break;

			default:
				break;
			}
			session.getTransaction().commit();
			session.close();

		}

	}

	private static void insertNewMaterials() {
		clearCMD();
		System.out.println("========================자재 입력========================");
		Scanner scanner = new Scanner(System.in);
		System.out.print("\t자재 이름을 입력하시오:");
		String name = scanner.nextLine();
		Material material = new Material(name);
		session.persist(material);
		printMaterials();
	}
	private static void insertNewProduct() {
		clearCMD();
		System.out.println("========================물품 입력========================");
		Scanner scanner = new Scanner(System.in);
		System.out.print("\t물품 이름을 입력하시오:");
		String name = scanner.nextLine();
		Product product = new Product(name);
		session.persist(product);
		printMaterials();
	}
	private static void insertNewBOM() {
		clearCMD();
		System.out.println("========================BOM 입력========================");
		Scanner scanner = new Scanner(System.in);
		System.out.print("\t제품 이름을 입력하시오:");
		String name = scanner.nextLine();
		
		
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Product> createQuery = builder.createQuery(Product.class);
		Root<Product> root = createQuery.from(Product.class);
		createQuery.select(root);
		createQuery.where(builder.equal(root.get("name"),name ));
		
		Query<Product> query = session.createQuery(createQuery);
		List<Product> list = query.list();
		if(list.isEmpty()==true) return;
		for(Product p :list) {
			System.out.println(p);
		}
		
		System.out.print("\t자재 이름을 입력하시오:");
		String mateName = scanner.nextLine();
		
		CriteriaQuery<Material> mateQuery = builder.createQuery(Material.class);
		Root<Material> mateRoot = mateQuery.from(Material.class);
		mateQuery.select(mateRoot);
		mateQuery.where(builder.equal(mateRoot.get("name"),mateName ));
		
		Query<Material> queryMate =  session.createQuery(mateQuery);
		List<Material> meList = queryMate.list();
		if(meList.isEmpty()==true)return;
		for(Material m : meList) {
			System.out.println(m);
		}
		System.out.print("\t자재의 갯수를 입력하시오:");
		int materialCount = Integer.parseInt(scanner.nextLine());
		Bom bom = new Bom(list.get(0), meList.get(0), materialCount);
		session.persist(bom);
		
	}
	private static void printMaterials() {
		Scanner scanner = new Scanner(System.in);
		ArrayList<Material>materials=(ArrayList<Material>) session.createQuery("From Material").getResultList();
		clearCMD();
		System.out.println("========================자재 목록========================");
		for(Material m : materials) {
			System.out.println(m);
		}
		System.out.println("=========================================================");
		scanner.nextLine();
	}

	private static void printProducts() {
		Scanner scanner = new Scanner(System.in);
		ArrayList<Product>products=(ArrayList<Product>) session.createQuery("From Product").getResultList();
		clearCMD();
		System.out.println("========================물품 목록========================");
		for(Product m : products) {
			System.out.println(m);
		}
		System.out.println("=========================================================");
		scanner.nextLine();
	}
	
	private static void printBOMs() {
		Scanner scanner = new Scanner(System.in);
		ArrayList<Bom>boms=(ArrayList<Bom>) session.createQuery("From Bom").getResultList();
		clearCMD();
		System.out.println("========================BOM 목록========================");
		for(Bom b : boms) {
			System.out.println(b);
		}
		System.out.println("=========================================================");
		scanner.nextLine();
	}
	
	private static void printMaterailMenu() {
		clearCMD();
		System.out.println("===================================");
		System.out.println("1. 자재 정보 출력");
		System.out.println("2. 자재 정보 입력");
		System.out.println("3. 자재 정보 수정");
		System.out.println("4. 자재 정보 삭제");
		System.out.println("5. 뒤로가기");
		System.out.println("===================================");
	}
	private static void printBOMMenu() {
		clearCMD();
		System.out.println("===================================");
		System.out.println("1. BOM 정보 출력");
		System.out.println("2. BOM 정보 입력");
		System.out.println("3. BOM 정보 수정");
		System.out.println("4. BOM 정보 삭제");
		System.out.println("5. 뒤로가기");
		System.out.println("===================================");
	}
	
	private static void printProductMenu() {
		clearCMD();
		System.out.println("===================================");
		System.out.println("1. 물품 정보 출력");
		System.out.println("2. 물품 정보 입력");
		System.out.println("3. 물품 정보 수정");
		System.out.println("4. 물품 정보 삭제");
		System.out.println("5. 뒤로가기");
		System.out.println("===================================");
	}

	private static void clearCMD() {
		System.out.print("\033\143");
	}

	private static void createOneEmployee() {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		session.beginTransaction();
		ArrayList<Project> projects = (ArrayList<Project>) session.createCriteria(Project.class).list();
		for (Project p : projects) {
			System.out.println(p);
		}

		Employee employee = new Employee();
		employee.setName("jungjiwon");
		HashMap<Integer, Project> hashMap = new HashMap<Integer, Project>();
		hashMap.put(10, projects.get(0));
		hashMap.put(11, projects.get(1));
		employee.setProjects(hashMap);
		session.persist(employee);
		session.getTransaction().commit();

	}

	private static void createOneProject() {
		// TODO Auto-generated method stub
//		Scanner scanner = new Scanner(System.in);
//
//		System.out.print("회사 이름을 입력하시오 : ");
//		String companyName = scanner.nextLine();
//		SupplyCompany company = new SupplyCompany();
//		company.setName(companyName);
//
//		session.beginTransaction();
//		System.out.println("저장중!");
//		session.save(company);
//		session.getTransaction().commit();

		String[] projectData = { "csCompany", "morisCompany" };
		Set<Project> projects = new HashSet<>();
		session.beginTransaction();
		System.out.println("저장중!");

		for (String proj : projectData) {
			session.save(new Project(proj));
		}
		session.getTransaction().commit();
	}

	private static int selectMenu() {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		int key = Integer.parseInt(scanner.nextLine());
		return key;
	}

	private static void printMenu() {
		// TODO Auto-generated method stub
		System.out.println("<<<<<<<<<<Menu>>>>>>>>>>");
		System.out.println("1.자재정보관리");
		System.out.println("2.제품정보관리");
		System.out.println("3.제품bom정보관리");

		System.out.println("========================");
	}

}
