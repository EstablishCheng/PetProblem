package com.shareprog.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.shareprog.constant.CommonConstant;
import com.shareprog.enums.PetSpecies;
import com.shareprog.pojo.Pet;
import com.shareprog.utils.SortUtil;

/**
 * 
 * <p>Title: PetService</p>  
 * <p>Description: </p> 
 * @author chengli
 * @date 2021年4月21日
 */
public class PetService {

	private ConcurrentHashMap<PetSpecies, AtomicInteger> petConcurrentHashMap = new ConcurrentHashMap<>();

	/**
	 * <p>Title: adoptPet</p>  
	 * <p>Description: 领养宠物</p>  
	 * @param requestMethod
	 * @return 响应结果
	 */
	public String adoptPet(String[] requestMethod) {
		if (requestMethod.length > 1) {
			PetSpecies petSpecies = PetSpecies.valueOf(requestMethod[1]);
			petConcurrentHashMap.computeIfPresent(petSpecies, (k, v) -> new AtomicInteger(v.incrementAndGet()));
			petConcurrentHashMap.computeIfAbsent(petSpecies, k -> new AtomicInteger(1));
			return CommonConstant.RESPONSE_SUCCESS;
		}
		return CommonConstant.RESPONSE_FAILURE;
	}

	/**
	 * <p>Title: listPet</p>  
	 * <p>Description: 查询宠物，使用lambda表达式可以很好的排序</p>  
	 * @return
	 */
	public String listPet1() {
		List<Pet> petList = new ArrayList<Pet>();
		Pet pet;
		for (Entry<PetSpecies, AtomicInteger> petEntry : petConcurrentHashMap.entrySet()) {
			pet = new Pet();
			pet.setSpecies(petEntry.getKey().name());
			pet.setQuantity(petEntry.getValue());
			petList.add(pet);
		}
		petList = petList.stream().sorted(Comparator.comparingInt(Pet::getQuantity)).collect(Collectors.toList());
		StringBuffer reponse = new StringBuffer();
		petList.forEach(p -> reponse.append(p.getSpecies()).append(": ").append(p.getQuantity()).append("\n"));
		return reponse.toString();
	}

	/**
	 * 
	 * <p>Title: listPet2</p>  
	 * <p>Description: 查询宠物，使用快速排序的话必须要将数据取出，不然对象拷贝会耗时</p>  
	 * @return
	 */
	public String listPet2() {
		int[] petArray = new int[petConcurrentHashMap.size()];
		int i = 0;
		Iterator<AtomicInteger> iterator = petConcurrentHashMap.values().iterator();
		while (iterator.hasNext()) {
			petArray[i] = iterator.next().get();
			i++;
		}
		SortUtil.sortPet(petArray);
		StringBuffer reponse = new StringBuffer();
		int repeat = 1;
		
		for (int j = 0; j < petArray.length; j += repeat) {
			int quantity = petArray[j];
			List<PetSpecies> petSpeciesList = petConcurrentHashMap.entrySet().stream()
					.filter(entry -> entry.getValue().get() == quantity )
					.map(Map.Entry::getKey)
					.collect(Collectors.toList());
			petSpeciesList.forEach(petSpecies -> 
				reponse.append(petSpecies.name()).append(": ").append(quantity).append("\n")
			);
			repeat = petSpeciesList.size();
		}
		return reponse.toString();
	}
	
	/**
	 * <p>Title: getResponse</p>  
	 * <p>Description: 返回特定格式的字符串</p>  
	 * @param petList
	 * @return 字符串
	 */
	private String getResponse(List<Pet> petList) {
		StringBuffer reponse = new StringBuffer();
		petList.forEach(p -> reponse.append(p.getSpecies()).append(": ").append(p.getQuantity()).append("\n"));
		return reponse.toString();
	}
	
}
