package teste;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import modelo.Empregado;
import modelo.SR;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.DateTool;

public class TestaEnvioDeEmail {

	public static void main(String[] args) {

		final StringWriter writer = preencheTemplate();
		
		
		enviarEmail(writer);
	}
	
	
	private static StringWriter preencheTemplate() {
		
		final StringWriter writer = new StringWriter();
		
		final VelocityEngine ve = new VelocityEngine();

		final VelocityContext context = new VelocityContext();
		
		final Template template = ve.getTemplate("/src/TemplateEmailHtml.vt");
		
		SR sr = new SR("123", "unidade", "unidade@unidade.com.br");
		
		Date dataCertificado = new Date();
		
		Empregado empregado1 = new Empregado(sr, "empregado1", "123456", "cpa10", dataCertificado);

		Empregado empregado2 = new Empregado(sr, "empregado2", "654321", "cpa20", dataCertificado);

		Empregado empregado3 = new Empregado(sr, "empregado3", "123456", "cpa10", dataCertificado);
		
		final List<Empregado> empregados = Arrays.asList(empregado1,empregado2, empregado3);

		ve.init();
		
		context.put("turma", "10");

		context.put("dataTurma", "10/10/2016");

		context.put("empregados", empregados);
		
		context.put("date", new DateTool());
		
		template.merge(context, writer);
		
		return writer;
	}

	private static void enviarEmail(final StringWriter writer) {
		
		final HtmlEmail email = new HtmlEmail();

		email.setHostName("correio.corp.caixa.gov.br");

		email.setAuthenticator(new DefaultAuthenticator("", ""));

		email.setSmtpPort(587);
		
		try {

			email.addTo("leo.m.silva@caixa.gov.br");

			email.setFrom("leo.m.silva@caixa.gov.br");

			email.setSubject("IMPORTANTE: Atualiza��o AMBIMA CPA10/CPA20");

			email.setMsg(writer.toString());

			email.send();
			
			System.out.println("email enviado");
			
		} catch (final EmailException e) {

			e.printStackTrace();
		}
	}
}