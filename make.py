#!/usr/bin/python
# -*- coding: utf-8 -*-

import fnmatch
import getopt
import os
import sys



def gen_tex(search_path, pattern):
    code = ""
    for root, dirnames, filenames in os.walk(search_path):
        for filename in fnmatch.filter(filenames, pattern):
            path = os.path.join(root, filename)
            s = '\\lstinputlisting[title=\large{%s}]{%s}' % (filename, path.replace('\\', '/'))
            code += '%s\n' % s
    return code


def replace(in_path, out_path, pattern, subst):
    # Create temp file
    with open(out_path, 'w') as new_file:
        with open(in_path) as old_file:
            for line in old_file:
                new_file.write(line.replace(pattern, subst))


def main(argv):
    tag = "%code%"
    input_tex = "lab-report.tex"
    output_tex = "out.tex"
    output_pdf = "out.pdf"
    search_path = "."
    pattern = "*.*"

    try:
        opts, args = getopt.getopt(argv, "i:o:d:p:", ["input=", "output=", "directory=", "pattern="])
    except getopt.GetoptError:
        print("error")
        sys.exit(2)
    for opt, arg in opts:
        if opt in ("-i", "--input"):
            input_tex = arg
        elif opt in ("-o", "--output"):
            output_pdf = arg
        elif opt in ("-d", "--directory"):
            search_path = arg
        elif opt in ("-p", "--pattern"):
            pattern = arg

    print("input=%s" % input_tex)
    print("output=%s" % output_pdf)

    code = gen_tex(search_path, pattern)
    replace(in_path=input_tex, out_path=output_tex, pattern=tag, subst=code)

    print("--- BUILD ---")
    os.system("latexmk --xelatex %s" % output_tex)

    print("--- COPY FILE ---")
    os.system("cp %s %s" % ("out.pdf", output_pdf))

    # Cleanup
    print("--- CLEANUP ---")
    os.system("latexmk -C --xelatex %s" % output_tex)
    os.remove(output_tex)

if __name__ == "__main__":
    main(sys.argv[1:])
