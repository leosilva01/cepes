package util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import modelo.Empregado;

public class EnviadorDeEmail {

	private static List<Empregado> emailsNaoEnviados = new ArrayList<>();

	public static void enviarEmail(final List<Empregado> empregados, final String CorpoDaMensagem, final String destinatario) throws InterruptedException {

		final HtmlEmail email = new HtmlEmail();

		email.setHostName("correio.corp.caixa.gov.br");

		email.setAuthenticator(new DefaultAuthenticator("", ""));

		email.setSmtpPort(587);

		try {

			email.addTo(destinatario);

			email.setFrom("leo.m.silva@caixa.gov.br");

			email.setSubject("IMPORTANTE: Atualização AMBIMA CPA10/CPA20");

			email.setMsg(CorpoDaMensagem);

			email.send();

			System.out.println("email enviado para " + destinatario);

			Thread.sleep(2800); // envio do email espera 2,5 segundos para evitar erro de limite rate no servidor de email.

		} catch (final EmailException e) {

			e.printStackTrace();

			EnviadorDeEmail.emailsNaoEnviados.addAll(empregados);

		}

		final GeradorDeExcel geradorDeExcel = new GeradorDeExcel(EnviadorDeEmail.emailsNaoEnviados);

		try {

			geradorDeExcel.geraExcel();

		} catch (final Exception e) {

			e.printStackTrace();
		}
	}
}
