package ca.mcgill.cs.NoviceHelper.views;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.ViewPart;

import ca.mcgill.cs.NoviceHelper.translator.Translator;
/**
 * NoviceHelperView listens to the Problems View
 * and depends on the Translator class to translate the
 * error messages and then displays the translated error
 * messages in an easy to read fashion.
 * @author Caroline Berger
 *
 */
public class NoviceHelperView extends ViewPart {
	private PageBook pagebook;
	private TableViewer tableviewer;
	private TextViewer textviewer;
	
	/*
	 * problemListener observes the Problems View,
	 * on clicks outside the Problems View, the 
	 * text displayed is updates to an empty String.
	 */
	private ISelectionListener problemListener = new ISelectionListener() {
		
		public void selectionChanged(IWorkbenchPart sourcepart, ISelection selection) {
			String partID = sourcepart.getSite().getId(); // where the current click is
			
			//only listen to problems view
			if (partID.equals("org.eclipse.ui.views.ProblemView")) { 
				showSelection(selection);
			}
			else if (sourcepart != NoviceHelperView.this ){
				showText(""); //show nothing
			}
		}
	};
	/**
	 * follows the selection down to get the individual Problems
	 * within the Problems View, involves type-casting into a tSel
	 * this method is called by selectionChanged and calls showText
	 * 
	 * @param selection ProblemView 
	 */
	private void showSelection(ISelection selection) {
		setContentDescription("Click on an error in the Problems View");
		/* used reflective programming (.getClass()) to figure out that
		 * the ProblemView is of type ITreeSelection 
		 */
		
		ITreeSelection tSel = (ITreeSelection) selection;
		TreePath[] paths = tSel.getPaths();
		
		if(paths.length==0){
			return;
		}
		/*
		 * Adaptor design pattern to get marker object.
		 */
		Object m = paths[0].getSegment(1);
		IAdaptable adaptable = (IAdaptable)m;  //type-caste
		IMarker marker = (IMarker)adaptable.getAdapter(IMarker.class);
		try {
			showText(marker.getAttribute(IMarker.MESSAGE).toString());
		} 
		catch (CoreException e) {
			e.printStackTrace();
		}
	}
	/**
	 * translates the error message by calling translate from Translator
	 * sets textviewer and pagebook to display the message
	 * 
	 * @param text untranslated error message
	 */
	private void showText(String text) {
		text = Translator.translate(text);
		textviewer.setDocument(new Document(text));
		pagebook.showPage(textviewer.getControl());
	}
	/**
	 * uses composite design pattern to manipulate appearance of Novice Error View
	 * @param parent auto-generated as part of ViewPart
	 */
	public void createPartControl(Composite parent) {
		//deal with appearance of View
		pagebook = new PageBook(parent, SWT.NONE);
		tableviewer = new TableViewer(pagebook, SWT.NONE);
		tableviewer.setLabelProvider(new WorkbenchLabelProvider());
		tableviewer.setContentProvider(new ArrayContentProvider());
		getSite().setSelectionProvider(tableviewer);
		textviewer = new TextViewer(pagebook, SWT.V_SCROLL | SWT.WRAP); //wrap text instead of scroll
		textviewer.setEditable(false);
		//register listener
		getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(problemListener);
	}
	
	/**
	 * inherited method. 
	 */
	public void setFocus() {
	}
	/**
	 * removes problem listener
	 * removes the Novice Helper View
	 */
	public void dispose() {
		getSite().getWorkbenchWindow().getSelectionService().removeSelectionListener(problemListener);
		super.dispose();
	}

}