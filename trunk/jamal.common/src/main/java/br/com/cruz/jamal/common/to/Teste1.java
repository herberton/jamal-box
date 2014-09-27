package br.com.cruz.jamal.common.to;

import lombok.Getter;
import lombok.Setter;

public class Teste1 extends JamalTO<Teste1> {

	private static final long serialVersionUID = -3010821839625136991L;

	@Getter
	@Setter
	private String t1;
	
	private String t2;
	
	@Getter
	@Setter
	public String t3;
	
	public String t4;
}
