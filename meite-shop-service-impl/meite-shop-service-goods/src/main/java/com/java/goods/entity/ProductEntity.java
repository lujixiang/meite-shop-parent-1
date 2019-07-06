package com.java.goods.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@Document(indexName = "goods", type = "goods")
@Data
public class ProductEntity {
	/** 主键ID */
	private Integer id;
	/** 类型ID */
	@JsonProperty("category_id")
	private Integer categoryId;
	/** 名称 */
	private String name;
	/** 小标题 */
	private String subtitle;
	/** 主图像 */
	@JsonProperty("main_image")
	private String mainImage;
	/** 小标题图像 */
	@JsonProperty("sub_images")
	private String subImages;
	/** 描述 */
	private String detail;
	/** 商品规格 */
	@JsonProperty("attribute_list")
	private String attributeList;
	/** 价格 */
	private Double price;
	/** 库存 */
	private Integer stock;
	/** 状态 */
	private Integer status;

	/** 创建人 */
	private String createdBy;
	/** 创建时间 */
	@JsonProperty("created_time")
	private Date createdTime;

	/** 更新时间 */
	@JsonProperty("updated_time")
	private Date updatedTime;
}