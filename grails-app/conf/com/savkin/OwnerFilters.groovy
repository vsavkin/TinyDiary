package com.savkin

class OwnerFilters {
    def filters = {
        all(controller:'post', action:'*') {
            before = {
                
            }
        }
    }
    
}
