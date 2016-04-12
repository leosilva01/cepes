package util;

import java.io.StringWriter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import modelo.Empregado;
import modelo.SR;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.DateTool;

public class GeradorDeEmail {

	public void geraEmail(final List<Empregado> todosEmpregados, final Set<SR> listaSR, final String turma, final String dataTurma) throws Exception {

		int i = 1;

		for (final SR sr : listaSR) {

			
			final StringWriter writer = new StringWriter();
			
			final VelocityEngine ve = new VelocityEngine();
			
			ve.init();
			
			final Template template = ve.getTemplate("/src/TemplateEmailHtml.vt");
			
			final VelocityContext context = new VelocityContext();

			context.put("turma", turma);

			context.put("dataTurma", dataTurma);

			final List<Empregado> empregadosPorSR = todosEmpregados.stream()
					.filter(e -> e.getSr().getCodigo()
					.equals(sr.getCodigo()))
					.collect(Collectors.toList());

			context.put("empregados", empregadosPorSR);
			
			context.put("date", new DateTool()); // Passa para o template formatar a data.

			template.merge(context, writer);
			
			System.out.println(i + " enviando e-mail para " + sr.getEmailSR());
			
			i++;
			
			EnviadorDeEmail.enviarEmail(empregadosPorSR, writer.toString(), "LEO.M.SILVA@CAIXA.GOV.BR");
			
		//	EnviadorDeEmail.enviarEmail(empregadosPorSR, writer.toString(), sr.getEmailSR());

		}
		
	}
}
