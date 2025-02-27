---
title: PMD CLI reference
summary: "Full reference for PMD's command-line interface, including options, output formats and supported languages"
tags: [userdocs]
keywords: [command, line, options, help, formats, renderers]
permalink: pmd_userdocs_cli_reference.html
author: Tom Copeland <tom@infoether.com>, Xavier Le Vourch <xlv@users.sourceforge.net>, Juan Martín Sotuyo Dodero <juansotuyo@gmail.com>
---


## Options

The tool comes with a rather extensive help text, simply running with `--help`!

<table>
    <tr>
        <th>Option</th>
        <th>Description</th>
        <th>Default value</th>
        <th>Applies to</th>
    </tr>

    {% include custom/cli_option_row.html options="--rulesets,-R"
               option_arg="refs"
               description="Comma-separated list of ruleset or rule references."
               required="yes"
    %}
    {% include custom/cli_option_row.html options="--dir,-d"
               option_arg="path"
               description="Root directory for sources to be analyzed. This can be a single file name, a directory,
                            or a jar or zip file containing the sources."
               required="yes"
    %}
    {% include custom/cli_option_row.html options="--format,-f"
               option_arg="format"
               description="Output format of the analysis report. The available formats
                            are described [here](#available-report-formats)."
               default="text"
    %}
     <tr><td/><td/><td/><td/></tr>
    {% include custom/cli_option_row.html options="--aux-classpath"
               option_arg="cp"
               description="Specifies the classpath for libraries used by the source code.
               This is used to resolve types in source files. The platform specific path delimiter
               (\":\" on Linux, \";\" on Windows) is used to separate the entries.
               Alternatively, a single `file:` URL
               to a text file containing path elements on consecutive lines can be specified."
               languages="Java"
    %}
    {% include custom/cli_option_row.html options="--benchmark,-b"
               description="Enables benchmark mode, which outputs a benchmark report upon completion.
                            The report is sent to standard error."
    %}
    {% include custom/cli_option_row.html options="--cache"
               option_arg="filepath"
               description="Specify the location of the cache file for incremental analysis.
                            This should be the full path to the file, including the desired file name (not just the parent directory).
                            If the file doesn't exist, it will be created on the first run. The file will be overwritten on each run
                            with the most up-to-date rule violations.
                            This can greatly improve analysis performance and is **highly recommended**."
    %}
    {% include custom/cli_option_row.html options="--debug,--verbose,-D,-V"
               description="Debug mode. Prints more log output."
    %}
    {% include custom/cli_option_row.html options="--encoding,-e"
               option_arg="charset"
               description="Specifies the character set encoding of the source code files PMD is reading.
                            The valid values are the standard character sets of `java.nio.charset.Charset`."
               default="UTF-8"
    %}
    {% include custom/cli_option_row.html options="--fail-on-violation"
               option_arg="bool"
               description="Specifies whether PMD exits with non-zero status if violations are found.
                            By default PMD exits with status 4 if violations are found.
                            Disable this feature with `--fail-on-violation false` to exit with 0 instead and just output the report."
               default="true"
    %}
    {% include custom/cli_option_row.html options="--file-list"
               option_arg="filepath"
               description="Path to file containing a list of files to analyze, one path per line.
                            If this is given, then you don't need to provide `--dir`."
    %}
    {% include custom/cli_option_row.html options="--force-language"
               option_arg="lang"
               description="Force a language to be used for all input files, irrespective of
                            file names. When using this option, the automatic language selection
                            by extension is disabled and PMD tries to parse all files with
                            the given language `&lt;lang&gt;`. Parsing errors are ignored and unparsable files
                            are skipped.
                            
                            <p>Use `--use-version` to specify the language version to use, if it is not the default.</p>

                            <p>This option allows to use the xml language for files, that don't
                            use xml as extension. See [example](#analyze-other-xml-formats) below.</p>"
    %}
    {% include custom/cli_option_row.html options="--ignore-list"
               option_arg="filepath"
               description="Path to file containing a list of files to ignore, one path per line.
                            This option can be combined with `--dir` and `--file-list`.
                            This ignore list takes precedence over any files in the file-list."
    %}
    {% include custom/cli_option_row.html options="--help,-h,-H"
               description="Display help on usage."
    %}
    {% include custom/cli_option_row.html options="--use-version"
               option_arg="lang-version"
               description="The specific language version PMD should use when parsing source code for a given language.
                            <p>Values are in the format of *language-version*.</p>
                            <p>This option can be repeated to configure several languages for the same run.</p>
                            <p>Note that this option does not change how languages are assigned to files.
                            It only changes something if the project you analyze contains some files that PMD detects as the given language.
                            Language detection is only influenced by file extensions and the `--force-language` option.</p>
                            <p>See also [Supported Languages](#supported-languages).</p>"
    %}
    {% include custom/cli_option_row.html options="-language,-l"
               option_arg="lang"
               description="Specify the language PMD should use. Used together with `-version`. See also [Supported Languages](#supported-languages).
                    <p><span class=\"label label-default\">Deprecated</span> since PMD 6.52.0. Use `--use-version` instead.</p>"
    %}
    {% include custom/cli_option_row.html options="--minimum-priority,-min"
               option_arg="num"
               description="Rule priority threshold; rules with lower priority than configured here won't be used."
               default="5"
    %}
    {% include custom/cli_option_row.html options="--no-ruleset-compatibility"
               description='Disable automatic fixing of invalid rule references. Without the switch, PMD tries to automatically replace rule references that point to moved or renamed rules with the newer location if possible. Disabling it is not recommended.'
    %}
    {% include custom/cli_option_row.html options="--no-cache"
               description="Explicitly disables incremental analysis. This switch turns off suggestions to use Incremental Analysis,
               and causes the `--cache` option to be discarded if it is provided."
    %}
    {% include custom/cli_option_row.html options="--property,-P"
               option_arg="name>=<value"
               description="Specifies a property for the report renderer. The option can be specified several times."
               default="[]"
    %}
    {% include custom/cli_option_row.html options="--relativize-paths-with,-z"
               option_arg="path"
               description="Path relative to which directories are rendered in the report. This option allows
                    shortening directories in the report; without it, paths are rendered as mentioned in the source directory (option \"--dir\").
                    The option can be repeated, in which case the shortest relative path will be used.
                    If the root path is mentioned (e.g. \"/\" or \"C:\\\"), then the paths will be rendered as absolute.
                    This option replaces `--short-names` since PMD 6.54.0."
    %}
    {% include custom/cli_option_row.html options="--report-file,-r"
               option_arg="path"
               description="Path to a file to which report output is written. The file is created if it does not exist. If this option is not specified, the report is rendered to standard output."
    %}
    {% include custom/cli_option_row.html options="--short-names"
               description="Prints shortened filenames in the report.
                    <p><span class=\"label label-default\">Deprecated</span> since PMD 6.54.0. Use `--relativize-paths-with` instead.</p>"
    %}
    {% include custom/cli_option_row.html options="--show-suppressed"
               description="Causes the suppressed rule violations to be added to the report."
    %}
    {% include custom/cli_option_row.html options="--stress,-S"
               description="Performs a stress test."
    %}
    {% include custom/cli_option_row.html options="--suppress-marker"
               option_arg="marker"
               description="Specifies the comment token that marks lines which PMD should ignore."
               default="NOPMD"
    %}
    {% include custom/cli_option_row.html options="--threads,-t"
               option_arg="num"
               description="Sets the number of threads used by PMD.
                            Set threads to `0` to disable multi-threading processing."
               default="1"
    %}
    {% include custom/cli_option_row.html options="--uri,-u"
                   option_arg="uri"
                   description="Database URI for sources. If this is given, then you don't need to provide `--dir`."
                   languages="PLSQL"
    %}
    {% include custom/cli_option_row.html options="--version"
                   description="Display current version of PMD and exit without performing any analysis."
    %}
    {% include custom/cli_option_row.html options="-version,-v"
               option_arg="version"
               description="Specify the version of a language PMD should use. Used together with `-language`. See also [Supported Languages](#supported-languages).
                    <p><span class=\"label label-default\">Deprecated</span> since PMD 6.52.0. Use `--use-version` instead.</p>"
    %}
</table>

## Additional Java Runtime Options

PMD is executed via a Java runtime. In some cases, you might need to set additional runtime options, e.g.
if you want to analyze a project, that uses one of OpenJDK's [Preview Language Features](http://openjdk.java.net/jeps/12).

Just set the environment variable `PMD_JAVA_OPTS` before executing PMD, e.g.

    export PMD_JAVA_OPTS="--enable-preview"
    ./run.sh pmd -d ../../../src/main/java/ -f text -R rulesets/java/quickstart.xml

## Exit Status

Please note that if PMD detects any violations, it will exit with status 4 (since 5.3).
This behavior has been introduced to ease PMD integration into scripts or hooks, such as SVN hooks.

<table>
<tr><td>0</td><td>Everything is fine, no violations found</td></tr>
<tr><td>1</td><td>Couldn't understand command-line parameters or PMD exited with an exception</td></tr>
<tr><td>4</td><td>At least one violation has been detected, unless <code>--fail-on-violation false</code> is set.</td></tr>
</table>


## Supported Languages

The language is determined automatically by PMD from the file extensions. Some languages such as "Java"
however support multiple versions. The default version will be used, which is usually the latest supported
non-preview version. If you want to use an older version, so that e.g. rules that suggest usage of language features
that are not available yet won't be executed, you need to specify a specific version via the `--use-version`
parameter.

These parameters are irrelevant for languages that don't support different versions.

Example:

``` shell
./run.sh pmd -d src/main/java -f text -R rulesets/java/quickstart.xml --use-version java-1.8
```

*   [apex](pmd_rules_apex.html) (Salesforce Apex)
*   [ecmascript](pmd_rules_ecmascript.html) (JavaScript)
*   [html](pmd_rules_html.html)
*   [java](pmd_rules_java.html)
    *   [Supported Versions](pmd_languages_java.html)
*   [jsp](pmd_rules_jsp.html)
*   [modelica](pmd_rules_modelica.html)
*   [plsql](pmd_rules_plsql.html)
*   [pom](pmd_rules_pom.html) (Maven POM)
*   [scala](pmd_rules_scala.html)
    *   Supported Versions: 2.10, 2.11, 2.12, 2.13 (default)
*   [vf](pmd_rules_vf.html) (Salesforce VisualForce)
*   [vm](pmd_rules_vm.html) (Apache Velocity)
*   [xml](pmd_rules_xml.html)
*   [xsl](pmd_rules_xsl.html)

## Available Report Formats

PMD comes with many different renderers.
All formats are described at [PMD Report formats](pmd_userdocs_report_formats.html)

## Examples

### Analyze other xml formats

If your xml language doesn't use `xml` as file extension, you can still use PMD with `--force-language`:

```
$ ./run.sh pmd -d /home/me/src/xml-file.ext -f text -R ruleset.xml --force-language xml
```

You can also specify a directory instead of a single file. Then all files are analyzed. In that case,
parse errors are suppressed in order to reduce irrelevant noise:

```
$ ./run.sh pmd -d /home/me/src/ -f text -R ruleset.xml --force-language xml
```

Alternatively, you can create a filelist to only analyze files with a given extension:

```
$ find /home/me/src -name "*.ext" > /home/me/src/filelist.txt
$ ./run.sh pmd --file-list /home/me/src/filelist.txt -f text -R ruleset.xml --force-language xml
```
