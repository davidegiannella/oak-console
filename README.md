oak-console
===========

A simple tool for browsing an OAK repository at NodeStore level.

Supported peristences
---------------------

h2, Tar

How to use it
-------------
the generic command line is like

    $ java -jar oak-console*.jar <persistence-type> <path-to-repo> [node to list]

1. Initiate a local oak repo (if not there)

        $ java -jar oak-run-*.jar server /tmp

2. Stop any Oak running.
3. List the content under root

        $ java -jar oak-console-*.jar h2 /tmp

4. List the content under a specified path

        $ java -jar oak-console-*.jar h2 /tmp /foo/bar

Output example
--------------

    $ java -jar ~/oak-console-0.0.1-SNAPSHOT-jar-with-dependencies.jar h2 /tmp /oak:index/nodetype
    {
        reindex = false,
        type = property,
        propertyNames = [jcr:primaryType, jcr:mixinTypes],
        jcr:primaryType = oak:QueryIndexDefinition,
        entryCount = 9223372036854775807,
    }
    + :index

