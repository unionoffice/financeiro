package br.com.unionoffice.document;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class ValorDocument extends PlainDocument {
	@Override
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		str = str.replace(",", ".");
		Pattern regex = Pattern.compile("^[0-9.]*");
		Matcher matcher = regex.matcher(str);
		if (!matcher.matches()) {
			return;
		}				
		String texto = this.getText(0, getLength());
		if (texto.contains(".")) {
			if (str.contains(".")) {
				return;
			}
			
		}
		super.insertString(offs, str, a);
	}

}
