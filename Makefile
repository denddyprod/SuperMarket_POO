build:
	javac Test.java

run: build
	for number in 0 1 2 3 4 5 6 7 8 9 ; do \
        cp ./Teste/Test0$$number/store.txt ./ ; \
		cp ./Teste/Test0$$number/customers.txt ./ ; \
		cp ./Teste/Test0$$number/events.txt ./ ; \
		java Test ; \
		cp ./output.txt ./Teste/Test0$$number/ ; \
    	done

clean:
	$(RM) *.class