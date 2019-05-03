package onetoManyMapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="bom")
public class Bom {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@OneToOne
	@JoinColumn(name = "material_id")
	private Material material;
	
	@OneToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	
	
	public Bom() {
		super();
	}

	private int materialCount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public int getMaterialCount() {
		return materialCount;
	}

	public void setMaterialCount(int materialCount) {
		this.materialCount = materialCount;
	}

	public Bom(Product product, Material material, int materialCount) {
		super();
		this.product = product;
		this.material = material;
		this.materialCount = materialCount;
	}

	@Override
	public String toString() {
		return "Bom [id=" + id + ", product=" + product + ", material=" + material + ", materialCount=" + materialCount
				+ "]";
	}
	
	
}
