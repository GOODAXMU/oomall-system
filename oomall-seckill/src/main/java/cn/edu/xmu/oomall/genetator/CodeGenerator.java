package cn.edu.xmu.oomall.genetator;

import cn.edu.xmu.oomall.entity.FlashSaleItemPo;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * @author xincong yao
 * @date 2020-11-1
 */
public class CodeGenerator {

	public static void main(String[] args) {
		Set<String> exclude = new HashSet<>();
		exclude.add("id");
		exclude.add("serialVersionUID");
		String r = generateSQL(FlashSaleItemPo.class, "sk", "goods", exclude);
		String c = generateCode(FlashSaleItemPo.class, "seckillItem", exclude);
		System.out.println(r);
		System.out.println("----------------------------");
		System.out.println(c);
	}

	public static String generateSQL(Class c, String abbreviation, String alias, Set<String> exclude) {
		Field[] fs = c.getDeclaredFields();
		String cName = c.getSimpleName();

		StringBuilder sb = new StringBuilder();

		sb.append("UPDATE " + cName + " " + abbreviation + " SET ");
		for (int i = 0; i < fs.length; i++) {
			if (exclude.contains(fs[i].getName())) {
				continue;
			}
			sb.append(abbreviation + "." + fs[i].getName())
					.append(" = CASE WHEN :#{#")
					.append(alias + "." + fs[i].getName())
					.append("} IS NULL THEN ")
					.append(abbreviation + "." + fs[i].getName())
					.append(" ELSE :#{#")
					.append(alias + "." + fs[i].getName())
					.append("} END, ");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.deleteCharAt(sb.length() - 1);
		sb.append(" WHERE ")
				.append(abbreviation + ".id")
				.append(" = :#{#")
				.append(alias + ".id")
				.append("}");

		return sb.toString();
	}

	public static String generateCode(Class c, String alias, Set<String> exclude) {
		Field[] fs = c.getDeclaredFields();

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < fs.length; i++) {
			if (exclude.contains(fs[i].getName())) {
				continue;
			}
			sb.append("if (" + alias + "." + getMethodName(fs[i].getName()) + "() != null) { ")
					.append("predicates.add(builder.equal(root.get(\"" + fs[i].getName() + "\"), ")
					.append(alias + "." + getMethodName(fs[i].getName()) + "())); }\n");
		}

		return sb.toString();

	}

	private static String getMethodName(String s) {
		return "get" + s.substring(0, 1).toUpperCase() + s.substring(1);
	}

}
