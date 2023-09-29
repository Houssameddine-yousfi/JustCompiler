package com.JsonAjax.justcompiler.Binding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.JsonAjax.justcompiler.VariableSymbol;

public class BoundScope {
    private Map<String,VariableSymbol> variables = new HashMap<>();
    private BoundScope parent;
    
    
    public BoundScope(BoundScope parent){
        this.parent = parent;
    }

    

    public BoundScope getParent() {
        return parent;
    }



    public Optional<VariableSymbol> tryLookup(String name){
        if(variables.containsKey(name))
            return Optional.of(variables.get(name));
        
        if(parent == null)
            return Optional.empty();
        
        return parent.tryLookup(name);
    }

    public boolean tryDeclare(VariableSymbol variable){
        if(variables.containsKey(variable.getName()))
            return false;

        variables.put(variable.getName(), variable);
        return true;
    }

    public List<VariableSymbol> getDeclaredVariables(){
        return new ArrayList<>(variables.values());
    }
}
