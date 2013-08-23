package net.minecraft.src.betterore.common.config.parser.validation;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

import net.minecraft.src.betterore.common.config.parser.BOLineAwareSAXHandler;
import net.minecraft.src.betterore.common.config.parser.BOParserException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.UserDataHandler;

public class BOValidatorImport extends BOValidatorNode
{
    protected boolean required = true;

    protected BOValidatorImport(BOValidatorNode parent, Node node, boolean required)
    {
        super(parent, node);
        this.required = required;
    }

    protected boolean validateChildren() throws BOParserException
    {
        super.validateChildren();
        File currentFile = (File)this.getNode().getOwnerDocument().getUserData("value");
        File baseDirectory = currentFile.getParentFile();
        String fileName = (String)this.validateRequiredAttribute(String.class, "file", true);
        List<File> files = getMatchingFiles(baseDirectory, fileName);
        this.getNode().setUserData("validated", Boolean.valueOf(true), (UserDataHandler)null);
        this.checkChildrenValid();

        if (files.isEmpty())
        {
            if (this.required)
            {
                throw new BOParserException("No files found matching \'" + fileName + "\'.", this.getNode());
            }

            this.replaceWithNode(new Node[0]);
        }
        else
        {
            ArrayList contents = new ArrayList(files.size());
            for (File file : files) {
            	Element importRoot = this.getNode().getOwnerDocument().createElement("ImportedDoc");
                this.getNode().appendChild(importRoot);
                importRoot.setUserData("value", file, (UserDataHandler)null);

                try
                {
                    this.getParser().getSaxParser().parse(file, new BOLineAwareSAXHandler(importRoot));
                }
                catch (Exception var10)
                {
                    throw new BOParserException(var10.getMessage(), this.getNode(), var10);
                }

                (new BOValidatorUnchecked(this, importRoot)).validate();
                contents.add(importRoot);

            }

            this.replaceWithNodeContents((Node[])contents.toArray(new Node[contents.size()]));
        }

        return false;
    }

    private static List getMatchingFiles(File baseDir, String relPath)
    {
        List<File> files = Arrays.asList(new File[] {(baseDir == null ? new File("") : baseDir).getAbsoluteFile()});
        Stack subPaths = new Stack();

        for (File subPath = new File(relPath); subPath != null; subPath = subPath.getParentFile())
        {
            subPaths.push(subPath.getName());
        }

        while (!subPaths.isEmpty())
        {
            String var12 = (String)subPaths.pop();
            LinkedList nextFiles = new LinkedList();
            for (File dir : files) {
                if (!var12.contains("*") && !var12.contains("?"))
                {
                    File var13 = new File(dir, var12);

                    if (var13.exists())
                    {
                        nextFiles.add(var13);
                    }
                }
                else
                {
                    File[] file = dir.listFiles(new WildcardFileFilter(var12));
                    for (File file1 : file) {
                    	if (file1.exists())
                        {
                            nextFiles.add(file1);
                        }
                    }
                }
            }
            files = nextFiles;
        }

        return (List)files;
    }
    
    public static class Factory implements IValidatorFactory<BOValidatorImport>
    {
        private final boolean _required;

        public Factory(boolean required)
        {
            this._required = required;
        }

        public BOValidatorImport createValidator(BOValidatorNode parent, Node node)
        {
            return new BOValidatorImport(parent, node, this._required);
        }
    }

    private static class WildcardFileFilter implements FilenameFilter
    {
        private Pattern _pattern;

        public WildcardFileFilter(String pattern)
        {
            this._pattern = Pattern.compile("\\Q" + pattern.replace("*", "\\E.*\\Q").replace("?", "\\E.\\Q") + "\\E");
        }

        public boolean accept(File dir, String fileName)
        {
            return this._pattern.matcher(fileName).matches();
        }
    }

}
