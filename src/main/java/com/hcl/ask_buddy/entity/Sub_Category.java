package com.hcl.ask_buddy.entity;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sub_cat")
public class Sub_Category {  

	// Sub-Category entity Attributes
	@Id
	@Column(name = "subcat_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
	private long id;
	@Column(name = "subcat_name")
	private String subcat_name;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "cat_id", referencedColumnName = "cat_id")
	private Category category;
	@JsonIgnore
	@OneToMany(mappedBy = "subCat", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Question> questionList;
}
